package eu.senla.Hotel.service;

import eu.senla.Hotel.api.sevice.IServiceService;
import eu.senla.Hotel.dao.ServiceDao;
import eu.senla.Hotel.model.Guest;
import eu.senla.Hotel.model.Service;

import java.util.ArrayList;
import java.util.logging.Logger;

public class HotelService implements IServiceService {
    public static final Logger logger = Logger.getLogger(
            HotelService.class.getName());

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
        if (newPrice<=0) {
            logger.info("Cost is below zero");
        }
        else {
            Service service = services.get(indexOrder);
            service.setPriceService(newPrice);
            services.set(indexOrder, service);
        }
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
