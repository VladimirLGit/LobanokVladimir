package eu.senla.hotel.service;

import eu.senla.hotel.api.dao.IGuestDao;
import eu.senla.hotel.api.sevice.IGuestService;
import eu.senla.hotel.exception.NotExistObject;
import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.Service;
import eu.senla.hotel.model.StateGuest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;


public class GuestService implements IGuestService {
    public static final Logger logger = Logger.getLogger(
            GuestService.class.getName());

    private final IGuestDao guestDao;
    public GuestService() {
        guestDao = null;
    }

    public GuestService(IGuestDao ds) {
        guestDao = ds;
    }

    @Override
    public void addGuest(Guest guest) {
        guestDao.addGuest(guest);
    }

    @Override
    public void deleteGuest(Guest guest) {
        try {
            guestDao.deleteGuest(guest);
        } catch (NotExistObject notExistObject) {
            logger.info(notExistObject.getMessage());
        }
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
        guestDao.addGuest(guest);
    }

    @Override
    public void leave(Guest guest) {
        try {
            guestDao.deleteGuest(guest);
        } catch (NotExistObject notExistObject) {
            logger.info(notExistObject.getMessage());
        }
    }

    public List<Guest> getGuests() {
        return guestDao.allGuests();
    }

    public void setGuests(List<Guest> guestList) {
        guestDao.setGuests(guestList);
    }


    @Override
    public void orderService(Guest guest, Service service) {
        guest.addOrderedService(service);
        //guestDao.addOrderGuest(guest, service);
    }

    @Override
    public List<Guest> last3Guests() {
        List<Guest> guests = guestDao.allGuests();
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
        return guestDao.allGuests().size();
    }

    @Override
    public Guest viewGuest(int indexGuest) {
        List<Guest> guests = guestDao.allGuests();
        if (indexGuest < guests.size()) {
            System.out.println(guests.get(indexGuest));
            return guests.get(indexGuest);
        } else
            System.out.println("Такого гостя нет");
        return null;
    }

    public void listGuests() {
        for (Guest guest : guestDao.allGuests()) {
            System.out.println(guest);
        }
    }
}
