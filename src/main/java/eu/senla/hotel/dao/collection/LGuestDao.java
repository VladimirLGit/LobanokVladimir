package eu.senla.hotel.dao.collection;

import eu.senla.hotel.api.dao.IGuestDao;
import eu.senla.hotel.exception.NotExistObject;
import eu.senla.hotel.model.Guest;

import java.util.ArrayList;
import java.util.List;

public class LGuestDao implements IGuestDao {

    private List<Guest> guests;

    public LGuestDao() {
        guests = new ArrayList<>();
    }

    @Override
    public void addGuest(Guest guest) {
        guests.add(guest);
    }

    @Override
    public void deleteGuest(Guest guest) throws NotExistObject {
        if (!guests.remove(guest))
            throw new NotExistObject("Not exist the " + guest.getName());
    }

    @Override
    public void updateGuest(Guest guest) throws NotExistObject {
        int i = guests.indexOf(guest);
        if (i!=-1) {
            guests.set(i, guest);
        }
        else
            throw new NotExistObject("Not exist the " + guest.getName());
    }

    @Override
    public List<Guest> allGuests() {
        return guests;
    }

    @Override
    public Guest readGuest(int id) {
        for (int i = 0; i < guests.size(); i++) {
            if (guests.get(i).getId() == id)
                return guests.get(i);
        }
        return null;
    }
}
