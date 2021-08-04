package eu.senla.hotel.service;

import eu.senla.hotel.api.dao.IGuestDao;
import eu.senla.hotel.api.sevice.IGuestService;
import eu.senla.hotel.dao.collection.LGuestDao;
import eu.senla.hotel.exception.NotExistObject;
import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.Guests;
import eu.senla.hotel.model.Service;
import eu.senla.hotel.model.StateGuest;

import javax.sql.DataSource;
import javax.xml.bind.annotation.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;


public class GuestService implements IGuestService {
    public static final Logger logger = Logger.getLogger(
            GuestService.class.getName());

    private IGuestDao guestDao;

    private Guests guestObjects;

    public GuestService() {
        guestObjects = new Guests();
        guestDao = null;
    }

    public GuestService(IGuestDao ds) {
        this();
        guestDao = ds;
        guestObjects.setGuestsList(guestDao.allGuests());
    }

    public Guests getGuestObjects() {
        return guestObjects;
    }

    public void setGuestObjects(Guests guestObjects) {
        this.guestObjects = guestObjects;
    }

    @Override
    public void addGuest(Guest guest) {
        //guests.add(guest);
        guestDao.addGuest(guest);
    }

    @Override
    public void deleteGuest(Guest guest) {
        guestObjects.setGuestsList(guestDao.allGuests());
        int index = guestObjects.getGuestsList().indexOf(guest);
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
        guestObjects.setGuestsList(guestDao.allGuests());
        int index = guestObjects.getGuestsList().indexOf(guest);
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
        guestObjects.setGuestsList(guestDao.allGuests());
        if (guestObjects.getGuestsList().size() == 0) {
            logger.info("no guests at the hotel");
        }
        return guestObjects.getGuestsList();
    }

    public void setGuestList(List<Guest> guestList) {
        this.guestObjects.setGuestsList(guestList);
    }


    @Override
    public void orderService(Guest guest, Service service) {
        guest.addOrderedService(service);
        //guestDao.addOrderGuest(guest, service);
    }

    @Override
    public List<Guest> last3Guests() {
        guestObjects.setGuestsList(guestDao.allGuests());
        List<Guest> last3Guest = new ArrayList<>();
        for (int i = guestObjects.getGuestsList().size() - 1; i >= 0; i--) {
            last3Guest.add(guestObjects.getGuestsList().get(i));
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
        guestObjects.setGuestsList(guestDao.allGuests());
        return guestObjects.getGuestsList().size();
    }

    @Override
    public Guest viewGuest(int indexGuest) {
        guestObjects.setGuestsList(guestDao.allGuests());
        if (indexGuest < guestObjects.getGuestsList().size()) {
            System.out.println(guestObjects.getGuestsList().get(indexGuest));
            return guestObjects.getGuestsList().get(indexGuest);
        } else
            System.out.println("Такого гостя нет");
        return null;
    }

    public void listGuests() {
        guestObjects.setGuestsList(guestDao.allGuests());
        for (Guest guest : guestObjects.getGuestsList()) {
            System.out.println(guest);
        }
    }
}
