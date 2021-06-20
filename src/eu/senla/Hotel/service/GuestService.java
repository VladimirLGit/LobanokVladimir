package eu.senla.Hotel.service;

import eu.senla.Hotel.api.sevice.IGuestService;
import eu.senla.Hotel.dao.GuestDao;
import eu.senla.Hotel.exception.NoGuestsInTheHotel;
import eu.senla.Hotel.exception.NotExistObject;
import eu.senla.Hotel.model.Guest;
import eu.senla.Hotel.model.Service;
import eu.senla.Hotel.model.StateGuest;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class GuestService implements IGuestService {
    public static final Logger logger = Logger.getLogger(
            GuestService.class.getName());
    static {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("src/eu/senla/Hotel/resources/logging.properties"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private final GuestDao guestDao;

    private final ArrayList<Guest> guests;

    public GuestService() {
        guestDao = new GuestDao();
        guests = guestDao.allGuests();
    }

    @Override
    public void addGuest(Guest guest) {
        guests.add(guest);
        guestDao.addGuest(guest);
    }

    @Override
    public void deleteGuest(Guest guest) {
        int index = guests.indexOf(guest);
        if (index!=-1){
            guest.setStateGuest(StateGuest.CHECK_OUT);
            try {
                guestDao.deleteGuest(guest);
            } catch (NotExistObject notExistObject) {
                logger.info(notExistObject.getMessage());
            }
            guests.remove(index);
        }
    }

    public void updateGuest(Guest guest) {
        guestDao.updateGuest(guest);
    }

    @Override
    public void enter(Guest guest) {
        Random RANDOM = new Random();
        LocalDate today = LocalDate.now();
        guest.setStateGuest(StateGuest.CHECK_IN);
        guest.setDateOfCheckIn(today);
        guest.setDateOfCheckOut(today.plusDays(RANDOM.nextInt(5)+1));
        guests.add(guest);
        guestDao.addGuest(guest);
    }

    @Override
    public void leave(Guest guest) {
        int index = guests.indexOf(guest);
        if (index!=-1){
            guest.setStateGuest(StateGuest.CHECK_OUT);
            try {
                guestDao.deleteGuest(guest);
            } catch (NotExistObject notExistObject) {
                logger.info(notExistObject.getMessage());
            }
            guests.remove(index);
        }
        else
            logger.info("The guest does not exist");
    }

    public ArrayList<Guest> getGuests() {
        if (guests.size()==0) {
            logger.info("no guests at the hotel");
        }
        return guests;
    }

    @Override
    public void orderService(Guest guest, Service service) {
        guest.addOrderedService(service);
        //guestDao.addOrderGuest(guest, service);
    }

    @Override
    public ArrayList<Guest> last3Guests() {
        ArrayList<Guest> last3Guest = new ArrayList<>();
        for (int i = guests.size()-1; i >= 0; i--) {
           last3Guest.add(guests.get(i));
           if (last3Guest.size()==3) break;
        }

        if (last3Guest.size()<3) {
            //если не хватает или нет гостей в списке, тогда последние из истории
            ArrayList<Guest> last3GuestHistory = guestDao.last3Guests();
            if (last3GuestHistory.size()>0)
                while ((last3Guest.size()!=3) && (last3GuestHistory.size()>0)){
                    last3Guest.add(last3GuestHistory.remove(0));
                }

        }
        return last3Guest;
    }

    @Override
    public int amountGuests() {
        return guests.size();
    }

    @Override
    public Guest viewGuest(int indexGuest) {
        if (indexGuest<guests.size()){
            System.out.println(guests.get(indexGuest));
            return guests.get(indexGuest);
        }
        else
            System.out.println("Такого гостя нет");
        return null;
    }

    public void listGuests() {
        for (Guest guest : guests) {
            System.out.println(guest);
        }
    }
}
