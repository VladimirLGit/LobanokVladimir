package eu.senla.Hotel.api.sevice;

import eu.senla.Hotel.model.Guest;
import eu.senla.Hotel.model.Service;

public interface IServiceService {
    void addService(Service service);
    void deleteService(Service service);
    void order(Guest guest, Service service);
    void listOrder();
    void changePriceOrder(int indexOrder, int newPrice);
    Service viewService(int indexService);
}
