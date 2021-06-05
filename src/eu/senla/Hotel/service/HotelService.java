package eu.senla.Hotel.service;

import eu.senla.Hotel.api.sevice.IServiceService;
import eu.senla.Hotel.dao.ServiceDao;
import eu.senla.Hotel.model.Guest;
import eu.senla.Hotel.model.Service;

import java.util.ArrayList;

public class HotelService implements IServiceService {
    private ServiceDao serviceDao;
    private ArrayList<Service> services;

    public HotelService() {
        serviceDao = new ServiceDao();
        services = new ArrayList<>();
    }

    @Override
    public void order(Guest guest, Service service) {
        System.out.println( service + " service done");
        serviceDao.addOrderGuest(guest, service);
    }
    public void addService(Service service){
        serviceDao.addService(service);
        services.add(service);
    }

    public void deleteService(Service service){
        serviceDao.deleteService(service);
        services.remove(service);
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    @Override
    public void listOrder() {
        services.forEach(service -> System.out.println(service));
    }

    @Override
    public void changePriceOrder(int indexOrder, int newPrice) {
        Service service = services.get(indexOrder);
        service.setPriceService(newPrice);
        services.set(indexOrder, service);
    }
}
