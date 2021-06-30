package eu.senla.hotel.api.dao;

import eu.senla.hotel.exception.NotExistObject;
import eu.senla.hotel.model.Guest;
import java.util.ArrayList;

public interface IGuestDao {
    void addGuest(Guest guest);
    void deleteGuest(Guest guest) throws NotExistObject;
    void updateGuest(Guest guest);
    ArrayList<Guest> allGuests();
}
