package eu.senla.hotel.api.dao;

import eu.senla.hotel.exception.NotExistObject;
import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.ServiceOrder;
import java.util.List;

public interface IServiceDao {
    void addService(ServiceOrder serviceOrder);
    void deleteService(ServiceOrder serviceOrder);
    void updateService(ServiceOrder serviceOrder);
    List<ServiceOrder> allServices();
    void addOrderGuest(Guest guest, ServiceOrder serviceOrder);
    void deleteOrderGuest(Guest guestOut) throws NotExistObject;
    ServiceOrder readService(Integer idService);
    void setServices(List<ServiceOrder> serviceOrders);
}
