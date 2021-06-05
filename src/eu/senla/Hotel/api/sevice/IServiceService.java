package eu.senla.Hotel.api.sevice;

import eu.senla.Hotel.model.Guest;
import eu.senla.Hotel.model.Service;

public interface IServiceService {
    void order(Guest guest, Service service);
    void listOrder();
    void changePriceOrder(int indexOrder, int newPrice);
}
