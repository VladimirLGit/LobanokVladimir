package eu.senla.hotel.dao.collection;

import eu.senla.hotel.api.dao.IServiceDao;
import eu.senla.hotel.exception.NotExistObject;
import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.Service;

import java.util.ArrayList;
import java.util.List;

public class LServiceDao implements IServiceDao {
    private List<Service> services;

    public LServiceDao() {
        services = new ArrayList<>();
    }

    @Override
    public void addService(Service service) {
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
