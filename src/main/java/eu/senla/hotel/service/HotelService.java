package eu.senla.hotel.service;

import eu.senla.hotel.api.dao.IGuestDao;
import eu.senla.hotel.api.dao.IServiceDao;
import eu.senla.hotel.api.sevice.IServiceService;
import eu.senla.hotel.dao.ServiceDao;
import eu.senla.hotel.dao.collection.LServiceDao;
import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.Service;
import eu.senla.hotel.model.Services;

import javax.sql.DataSource;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class HotelService implements IServiceService {
    public static final Logger logger = Logger.getLogger(
            HotelService.class.getName());

    private IServiceDao serviceDao;

    public HotelService() {
        serviceDao = null;
    }

    public HotelService(IServiceDao ds) {
        serviceDao = ds;
    }

    @Override
    public void order(Guest guest, Service service) {
        System.out.println( service + " service done");
        serviceDao.addOrderGuest(guest, service);
    }

    public void addService(Service service){
        serviceDao.addService(service);
    }
    public void deleteService(Service service){
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
        if (newPrice<=0) {
            logger.info("Cost is below zero");
        }
        else {
            Service service = serviceDao.allServices().get(indexOrder);
            service.setPrice(newPrice);
            serviceDao.updateService(service);
        }
    }

    @Override
    public Service viewService(int indexService) {
        List<Service> services = serviceDao.allServices();
        if (indexService<services.size()){
            System.out.println(services.get(indexService));
            return services.get(indexService);
        }
        else
            System.out.println("Такой услуги не существует");
        return null;
    }
}
