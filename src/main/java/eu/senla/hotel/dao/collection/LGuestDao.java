package eu.senla.hotel.dao.collection;

import eu.senla.hotel.api.dao.IGuestDao;
import eu.senla.hotel.dependency2.annotation.Component;
import eu.senla.hotel.exception.NotExistObject;
import eu.senla.hotel.model.Guest;
import eu.senla.mysql.model.PC;

import java.util.ArrayList;
import java.util.List;

@Component
public class LGuestDao implements IGuestDao {
    private List<Guest> guests;

    public LGuestDao() {
        guests = new ArrayList<>();
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }

    @Override
    public void addGuest(Guest guest) {
        int newId = guests.size() == 0 ? 0 : guests.stream()
                .mapToInt(Guest::getId)
                .summaryStatistics()
                .getMax();
        guest.setId(newId + 1);
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
            guest.setId(guests.get(i).getId());
            guests.set(i, guest);
        }
        else
            throw new NotExistObject("Not exist the " + guest.getName());
    }

    @Override
    public List<Guest> allGuests() {
        return guests;
    }

    public List<Guest> last3Guests() {
        List<Guest> last3Guest = new ArrayList<>();
        for (int i = guests.size()-1; i >= 0; i--) {
            last3Guest.add(guests.get(i));
            if (last3Guest.size()==3) break;
        }
        return last3Guest;
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
