package eu.senla.hotel.api.sevice;

import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.ServiceOrder;

import java.util.List;

public interface IGuestService {
    void addGuest(Guest guest);
    void deleteGuest(Guest guest);
    Guest viewGuest(int indexGuest);
    void enter(Guest guest);
    void leave(Guest guest);
    void orderService(Guest guest, ServiceOrder serviceOrder);
    List<Guest> last3Guests();
    int amountGuests();
    List<Guest> getGuests();
    void setGuests(List<Guest> guests);
    void updateGuest(Guest guest);
    void listGuests();

}

