package eu.senla.Hotel.api.dao;

import eu.senla.Hotel.model.Service;

import java.util.ArrayList;

public interface IServiceDao {
    void addService(Service service);
    void deleteService(Service service);
    void updateService(Service service);
    ArrayList<Service> allServices();
}
