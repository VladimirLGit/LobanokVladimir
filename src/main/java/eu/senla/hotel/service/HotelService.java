package eu.senla.hotel.service;

import eu.senla.hotel.api.dao.IServiceDao;
import eu.senla.hotel.api.sevice.IServiceService;
import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class HotelService implements IServiceService {
    private static final Logger logger = LogManager.getLogger();

    private IServiceDao serviceDao;

    public HotelService() {
        serviceDao = null;
    }

    public HotelService(IServiceDao ds) {
        serviceDao = ds;
    }

    @Override
    public void order(Guest guest, Service service) {
        System.out.println(service + " service done");
        if ((guest != null) && (service != null))
            serviceDao.addOrderGuest(guest, service);
        else
           logger.info("guest is null or service is null");
    }

    public void addService(Service service) {
        serviceDao.addService(service);
    }

    public void deleteService(Service service) {
        serviceDao.deleteService(service);
    }

    public List<Service> getServices() {
        return serviceDao.allServices();
    }

    public void setServices(List<Service> services) {
        if (services != null)
            this.serviceDao.setServices(services);
    }

    @Override
    public void listOrder() {
        serviceDao.allServices().forEach(System.out::println);
    }

    @Override
    public void changePriceOrder(int indexOrder, int newPrice) {
        if (newPrice <= 0) {
            logger.info("Cost is below zero");
        } else {
            Service service = serviceDao.allServices().get(indexOrder);
            service.setPrice(newPrice);
            serviceDao.updateService(service);
        }
    }

    @Override
    public Service viewService(int indexService) {
        List<Service> services = serviceDao.allServices();
        if (indexService < services.size()) {
            System.out.println(services.get(indexService));
            return services.get(indexService);
        } else
            System.out.println("Такой услуги не существует");
        return null;
    }
}
