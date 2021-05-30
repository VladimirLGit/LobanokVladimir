package eu.senla.Hotel.api.dao;

import eu.senla.Hotel.model.Guest;
import java.util.ArrayList;

public interface IGuestDao {
    void addGuest(Guest guest);
    void deleteGuest(Guest guest);
    void updateGuest(Guest guest);
    ArrayList<Guest> allGuests();
}
