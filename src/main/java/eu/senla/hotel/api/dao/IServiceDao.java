package eu.senla.hotel.api.dao;

import eu.senla.hotel.model.Service;
import java.util.List;

public interface IServiceDao {
    void addService(Service service);
    void deleteService(Service service);
    void updateService(Service service);
    List<Service> allServices();
}
