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
    private Services serviceObjects;

    public HotelService() {
        serviceObjects = new Services();
        serviceDao = null;
    }

    public HotelService(IServiceDao ds) {
        this();
        serviceDao = ds;
        serviceObjects.setServices(serviceDao.allServices());
    }

    @Override
    public Services getServiceObjects() {
        return serviceObjects;
    }

    public void setServiceObjects(Services serviceObjects) {
        this.serviceObjects = serviceObjects;
        this.serviceDao.setServices(serviceObjects.getServices());
    }
    public void reloadDao(IServiceDao ds) {
        serviceDao = ds;
    }

    @Override
    public void order(Guest guest, Service service) {
        System.out.println( service + " service done");
        serviceDao.addOrderGuest(guest, service);
    }

    public void addService(Service service){
        serviceDao.addService(service);
        //services.add(service);
    }
    public void deleteService(Service service){
        serviceDao.deleteService(service);
        //services.remove(service);
    }

    public List<Service> getServices() {
        serviceObjects.setServices(serviceDao.allServices());
        return serviceObjects.getServices();
    }
    public void setServices(ArrayList<Service> services) {
        this.serviceObjects.setServices(services);
        this.serviceDao.setServices(services);
    }

    @Override
    public void listOrder() {
        serviceObjects.setServices(serviceDao.allServices());
        serviceObjects.getServices().forEach(System.out::println);
    }

    @Override
    public void changePriceOrder(int indexOrder, int newPrice) {
        if (newPrice<=0) {
            logger.info("Cost is below zero");
        }
        else {
            serviceObjects.setServices(serviceDao.allServices());
            Service service = serviceObjects.getServices().get(indexOrder);
            service.setPrice(newPrice);
            serviceObjects.getServices().set(indexOrder, service);
        }
    }

    @Override
    public Service viewService(int indexService) {
        serviceObjects.setServices(serviceDao.allServices());
        if (indexService<serviceObjects.getServices().size()){
            System.out.println(serviceObjects.getServices().get(indexService));
            return serviceObjects.getServices().get(indexService);
        }
        else
            System.out.println("Такой услуги не существует");
        return null;
    }
}
