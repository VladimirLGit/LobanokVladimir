package eu.senla.hotel.api.dao;

import eu.senla.hotel.model.Guest;

import java.util.List;

public interface IGuestDao {
    void addGuest(Guest guest);
    void deleteGuest(Guest guest);
    void updateGuest(Guest guest);
    List<Guest> allGuests();
}
