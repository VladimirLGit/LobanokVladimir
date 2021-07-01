package main.java.eu.senla.hotel.api.dao;

import main.java.eu.senla.hotel.exception.NotExistObject;
import main.java.eu.senla.hotel.model.Guest;

import java.util.List;

public interface IGuestDao {
    void addGuest(Guest guest);
    void deleteGuest(Guest guest) throws NotExistObject;
    void updateGuest(Guest guest) throws NotExistObject;
    List<Guest> allGuests();
    Guest readGuest(int id);
}
