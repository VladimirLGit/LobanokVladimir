package main.java.eu.senla.hotel.api.sevice;

import main.java.eu.senla.hotel.model.Guest;
import main.java.eu.senla.hotel.model.Service;

import java.util.List;

public interface IServiceService {
    void addService(Service service);
    void deleteService(Service service);
    void order(Guest guest, Service service);
    void listOrder();
    void changePriceOrder(int indexOrder, int newPrice);
    Service viewService(int indexService);

    List<Service> getServices();
}
