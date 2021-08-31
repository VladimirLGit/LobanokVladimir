package eu.senla.hotel.dao.collection;

import eu.senla.hotel.api.dao.IServiceDao;
import eu.senla.hotel.dependency2.annotation.Component;
import eu.senla.hotel.exception.NotExistObject;
import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.ServiceOrder;

import java.util.ArrayList;
import java.util.List;

@Component
public class LServiceDao implements IServiceDao {
    private List<ServiceOrder> serviceOrders;

    public LServiceDao() {
        serviceOrders = new ArrayList<>();
    }

    public List<ServiceOrder> getServices() {
        return serviceOrders;
    }

    public void setServices(List<ServiceOrder> serviceOrders) {
        this.serviceOrders = serviceOrders;
    }

    @Override
    public void addService(ServiceOrder serviceOrder) {
        int newId = serviceOrders.size() == 0 ? 0 : serviceOrders.stream()
                .mapToInt(ServiceOrder::getId)
                .summaryStatistics()
                .getMax();
        serviceOrder.setId(newId + 1);
        serviceOrders.add(serviceOrder);
    }

    @Override
    public void deleteService(ServiceOrder serviceOrder) {
        serviceOrders.remove(serviceOrder);
    }

    @Override
    public void updateService(ServiceOrder serviceOrder) {
        int i = serviceOrders.indexOf(serviceOrder);
        if (i!=-1) {
            serviceOrder.setId(serviceOrders.get(i).getId());
            serviceOrders.set(i, serviceOrder);
        }
    }

    @Override
    public List<ServiceOrder> allServices() {
        return serviceOrders;
    }

    @Override
    public void addOrderGuest(Guest guest, ServiceOrder serviceOrder) {
        guest.addOrderedService(serviceOrder);
    }

    @Override
    public void deleteOrderGuest(Guest guestOut) throws NotExistObject {
        guestOut.clearListOrder();
    }

    @Override
    public ServiceOrder readService(Integer idService) {
        for (ServiceOrder serviceOrder : serviceOrders) {
            if (serviceOrder.getId().equals(idService))
                return serviceOrder;
        }
        return null;
    }
}
