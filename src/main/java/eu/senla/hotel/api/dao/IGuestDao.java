package eu.senla.hotel.api.dao;

import eu.senla.hotel.exception.NotExistObject;
import eu.senla.hotel.model.Guest;

import java.util.List;

public interface IGuestDao {
    void addGuest(Guest guest);
    void deleteGuest(Guest guest) throws NotExistObject;
    void updateGuest(Guest guest) throws NotExistObject;
    List<Guest> allGuests();
    Guest readGuest(int id);
}
