package eu.senla.Hotel.service;

import eu.senla.Hotel.api.sevice.IGuestService;
import eu.senla.Hotel.dao.GuestDao;
import eu.senla.Hotel.model.Guest;
import eu.senla.Hotel.model.Service;
import eu.senla.Hotel.model.StateGuest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class GuestService implements IGuestService {
    private final GuestDao guestDao;

    private final ArrayList<Guest> guests;

    public GuestService() {
        guestDao = new GuestDao();
        guests = guestDao.allGuests();
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
            guestDao.deleteGuest(guest);
            guests.remove(index);
        }
    }

    public ArrayList<Guest> getGuests() {
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
}
