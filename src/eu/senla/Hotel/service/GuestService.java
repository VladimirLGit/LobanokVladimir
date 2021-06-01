package eu.senla.Hotel.service;

import eu.senla.Hotel.api.sevice.IGuestService;
import eu.senla.Hotel.dao.GuestDao;
import eu.senla.Hotel.model.Guest;
import eu.senla.Hotel.model.Service;
import eu.senla.Hotel.model.StateGuest;

import java.util.ArrayList;

public class GuestService implements IGuestService {
    private final GuestDao guestDao;
    private final ArrayList<Guest> guests;

    public GuestService() {
        guestDao = new GuestDao();
        guests = guestDao.allGuests();
    }

    @Override
    public void enter(Guest guest) {
        guest.setStateGuest(StateGuest.CHECK_IN);
        guestDao.addGuest(guest);
        guests.add(guest);
    }

    @Override
    public void leave(Guest guest) {
        guest.setStateGuest(StateGuest.CHECK_OUT);
        guestDao.deleteGuest(guest);
        guestDao.deleteOrderGuest(guest);
        guests.remove(guest);
    }

    @Override
    public void orderService(Guest guest, Service service) {
        guest.addOrderedService(service);
        guestDao.addOrderGuest(guest, service);
    }

    @Override
    public ArrayList<Guest> last3Guests() {
        ArrayList<Guest> last3Guest = new ArrayList<>();
        if (guests.size()>=3){
            for (int i = guests.size()-1; i > 0; i--) {
               last3Guest.add(guests.get(i));
            }
        }
        else {
            //если нет гостей в списке, тогда последние из истории
            last3Guest.addAll(guestDao.last3Guests());
        }
        return last3Guest;
    }

    @Override
    public int amountGuests() {
        return guests.size();
    }
}
