package eu.senla.hotel.service;

import eu.senla.hotel.api.sevice.IServiceService;
import eu.senla.hotel.dao.ServiceDao;
import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.Service;

import java.util.ArrayList;
import java.util.List;

public class HotelService implements IServiceService {
    private ServiceDao serviceDao;
    private List<Service> services;

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

    public List<Service> getServices() {
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

    @Override
    public Service viewService(int indexService) {
        if (indexService<services.size()){
            System.out.println(services.get(indexService));
            return services.get(indexService);
        }
        else
            System.out.println("Такой услуги не существует");
        return null;
    }
}
