package main.java.eu.senla.hotel.api.sevice;

import main.java.eu.senla.hotel.model.Guest;
import main.java.eu.senla.hotel.model.Service;
import java.util.List;

public interface IGuestService {
    void addGuest(Guest guest);
    void deleteGuest(Guest guest);
    Guest viewGuest(int indexGuest);
    void enter(Guest guest);
    void leave(Guest guest);
    void orderService(Guest guest, Service service);
    List<Guest> last3Guests();
    int amountGuests();
    List<Guest> getGuests();

    void updateGuest(Guest guest);

    void listGuests();
}

