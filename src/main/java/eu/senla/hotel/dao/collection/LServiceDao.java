package eu.senla.hotel.dao.collection;

import eu.senla.hotel.api.dao.IServiceDao;
import eu.senla.hotel.dependency2.annotation.Component;
import eu.senla.hotel.exception.NotExistObject;
import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.Room;
import eu.senla.hotel.model.Service;

import java.util.ArrayList;
import java.util.List;

@Component
public class LServiceDao implements IServiceDao {
    private List<Service> services;

    public LServiceDao() {
        services = new ArrayList<>();
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    @Override
    public void addService(Service service) {
        int newId = services.size() == 0 ? 0 : services.stream()
                .mapToInt(Service::getId)
                .summaryStatistics()
                .getMax();
        service.setId(newId + 1);
        services.add(service);
    }

    @Override
    public void deleteService(Service service) {
        services.remove(service);
    }

    @Override
    public void updateService(Service service) {
        int i = services.indexOf(service);
        if (i!=-1) {
            service.setId(services.get(i).getId());
            services.set(i, service);
        }
    }

    @Override
    public List<Service> allServices() {
        return services;
    }

    @Override
    public void addOrderGuest(Guest guest, Service service) {
        guest.addOrderedService(service);
    }

    @Override
    public void deleteOrderGuest(Guest guestOut) throws NotExistObject {
        guestOut.clearListOrder();
    }

    @Override
    public Service readService(Integer idService) {
        for (Service service : services) {
            if (service.getId().equals(idService))
                return service;
        }
        return null;
    }
}
