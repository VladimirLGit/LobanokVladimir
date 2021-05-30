package eu.senla.Hotel.service;

import eu.senla.Hotel.api.sevice.IGuestService;
import eu.senla.Hotel.model.Guest;
import eu.senla.Hotel.model.Service;
import eu.senla.Hotel.model.StateGuest;

import java.util.ArrayList;

public class GuestService implements IGuestService {
    private ArrayList<Guest> guests;
    @Override
    public void enter(Guest guest) {
        guest.setStateGuest(StateGuest.CHECK_IN);
        guests.add(guest);
    }

    @Override
    public void leave(Guest guest) {
        guest.setStateGuest(StateGuest.CHECK_OUT);
        guests.remove(guest);
    }

    @Override
    public void orderService(Guest guest, Service service) {
        guest.addOrderedService(service);
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
        }
        return last3Guest;
    }

    @Override
    public int amountGuests() {
        return guests.size();
    }
}
