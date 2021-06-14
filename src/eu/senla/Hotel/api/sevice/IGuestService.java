package eu.senla.Hotel.api.sevice;

import eu.senla.Hotel.model.Guest;
import eu.senla.Hotel.model.Service;

import java.util.ArrayList;

public interface IGuestService {
    void addGuest(Guest guest);
    void deleteGuest(Guest guest);
    Guest viewGuest(int indexGuest);
    void enter(Guest guest);
    void leave(Guest guest);
    void orderService(Guest guest, Service service);
    ArrayList<Guest> last3Guests();
    int amountGuests();


}

