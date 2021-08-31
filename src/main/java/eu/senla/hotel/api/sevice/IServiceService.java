package eu.senla.hotel.api.sevice;

import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.ServiceOrder;

import java.util.List;

public interface IServiceService {
    void addService(ServiceOrder serviceOrder);
    void deleteService(ServiceOrder serviceOrder);
    void order(Guest guest, ServiceOrder serviceOrder);
    void listOrder();
    void changePriceOrder(int indexOrder, int newPrice);
    ServiceOrder viewService(int indexService);
    List<ServiceOrder> getServices();
    void setServices(List<ServiceOrder> serviceOrders);
}

