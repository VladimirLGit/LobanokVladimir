package main.java.eu.senla.hotel.api.dao;

import main.java.eu.senla.hotel.exception.NotExistObject;
import main.java.eu.senla.hotel.model.Guest;
import main.java.eu.senla.hotel.model.Service;
import java.util.List;

public interface IServiceDao {
    void addService(Service service);
    void deleteService(Service service);
    void updateService(Service service);
    List<Service> allServices();
    void addOrderGuest(Guest guest, Service service);
    void deleteOrderGuest(Guest guestOut) throws NotExistObject;
    Service readService(Integer idService);
}
