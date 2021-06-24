package eu.senla.hotel.api.sevice;

import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.Service;

import java.util.ArrayList;

public interface IGuestService {
    void enter(Guest guest);
    void leave(Guest guest);
    void orderService(Guest guest, Service service);
    ArrayList<Guest> last3Guests();
    int amountGuests();
}
