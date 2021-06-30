package eu.senla.hotel.api.dao;

import eu.senla.hotel.model.Service;

import java.util.ArrayList;

public interface IServiceDao {
    void addService(Service service);
    void deleteService(Service service);
    void updateService(Service service);
    ArrayList<Service> allServices();
}
