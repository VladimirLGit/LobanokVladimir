package eu.senla.hotel.service;

import eu.senla.hotel.api.dao.IGuestDao;
import eu.senla.hotel.api.sevice.IGuestService;
import eu.senla.hotel.dao.collection.LGuestDao;
import eu.senla.hotel.exception.NotExistObject;
import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.Service;
import eu.senla.hotel.model.StateGuest;

import javax.sql.DataSource;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@XmlType(name = "guestService")
@XmlRootElement
public class GuestService implements IGuestService {
    public static final Logger logger = Logger.getLogger(
            GuestService.class.getName());

    private IGuestDao guestDao;
    @XmlElementWrapper(name = "guestList")
    // XmlElement sets the name of the entities
    @XmlElement(name = "Guests")
    private List<Guest> guests;

    public GuestService() {
        guestDao = null;
    }

    public GuestService(IGuestDao ds) {
        guestDao = ds;
        guests = guestDao.allGuests();
    }

    @Override
    public void addGuest(Guest guest) {
        //guests.add(guest);
        guestDao.addGuest(guest);
    }

    @Override
    public void deleteGuest(Guest guest) {
        guests = guestDao.allGuests();
        int index = guests.indexOf(guest);
        if (index != -1) {
            guest.setState(StateGuest.CHECK_OUT);
            try {
                guestDao.deleteGuest(guest);
            } catch (NotExistObject notExistObject) {
                logger.info(notExistObject.getMessage());
            }
            //guests.remove(index);
        }
    }

    public void reloadDao(IGuestDao ds) {
        guestDao = ds;
    }

    public void updateGuest(Guest guest) {
        try {
            guestDao.updateGuest(guest);
        } catch (NotExistObject notExistObject) {
            logger.info(notExistObject.getMessage());
        }
    }

    @Override
    public void enter(Guest guest) {
        Random RANDOM = new Random();
        LocalDate today = LocalDate.now();
        guest.setState(StateGuest.CHECK_IN);
        guest.setDateOfCheckIn(today);
        guest.setDateOfCheckOut(today.plusDays(RANDOM.nextInt(5) + 1));
        //guests.add(guest);
        guestDao.addGuest(guest);
    }

    @Override
    public void leave(Guest guest) {
        guests = guestDao.allGuests();
        int index = guests.indexOf(guest);
        if (index != -1) {
            guest.setState(StateGuest.CHECK_OUT);
            try {
                guestDao.deleteGuest(guest);
            } catch (NotExistObject notExistObject) {
                logger.info(notExistObject.getMessage());
            }
            //guests.remove(index);
        } else
            logger.info("The guest does not exist");
    }

    public List<Guest> getGuests() {
        if (guestDao == null)
            return guests;
        guests = guestDao.allGuests();
        if (guests.size() == 0) {
            logger.info("no guests at the hotel");
        }
        return guests;
    }

    public void setGuestList(List<Guest> guestList) {
        this.guests = guestList;
    }

    @Override
    public void orderService(Guest guest, Service service) {
        guest.addOrderedService(service);
        //guestDao.addOrderGuest(guest, service);
    }

    @Override
    public List<Guest> last3Guests() {
        guests = guestDao.allGuests();
        List<Guest> last3Guest = new ArrayList<>();
        for (int i = guests.size() - 1; i >= 0; i--) {
            last3Guest.add(guests.get(i));
            if (last3Guest.size() == 3) break;
        }

        if (last3Guest.size() < 3) {
            //если не хватает или нет гостей в списке, тогда последние из истории
            List<Guest> last3GuestHistory = guestDao.last3Guests();
            if (last3GuestHistory.size() > 0)
                while ((last3Guest.size() != 3) && (last3GuestHistory.size() > 0)) {
                    last3Guest.add(last3GuestHistory.remove(0));
                }

        }
        return last3Guest;
    }

    @Override
    public int amountGuests() {
        guests = guestDao.allGuests();
        return guests.size();
    }

    @Override
    public Guest viewGuest(int indexGuest) {
        guests = guestDao.allGuests();
        if (indexGuest < guests.size()) {
            System.out.println(guests.get(indexGuest));
            return guests.get(indexGuest);
        } else
            System.out.println("Такого гостя нет");
        return null;
    }

    public void listGuests() {
        guests = guestDao.allGuests();
        for (Guest guest : guests) {
            System.out.println(guest);
        }
    }
}
