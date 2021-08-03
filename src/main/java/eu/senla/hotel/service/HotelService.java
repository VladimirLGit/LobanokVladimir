package eu.senla.hotel.service;

import eu.senla.hotel.api.dao.IGuestDao;
import eu.senla.hotel.api.dao.IServiceDao;
import eu.senla.hotel.api.sevice.IServiceService;
import eu.senla.hotel.dao.ServiceDao;
import eu.senla.hotel.dao.collection.LServiceDao;
import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.Service;

import javax.sql.DataSource;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@XmlType(name = "hotelService")
@XmlRootElement
public class HotelService implements IServiceService {
    public static final Logger logger = Logger.getLogger(
            HotelService.class.getName());

    private IServiceDao serviceDao;

    @XmlElementWrapper(name = "serviceList")
    @XmlElement(name = "Service")
    private List<Service> services;

    public HotelService() {
        serviceDao = null;
    }

    public HotelService(IServiceDao ds) {
        serviceDao = ds;
        services = serviceDao.allServices();
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
        if (serviceDao == null)
            return services;
        services = serviceDao.allServices();
        return services;
    }
    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }

    @Override
    public void listOrder() {
        services = serviceDao.allServices();
        services.forEach(System.out::println);
    }

    @Override
    public void changePriceOrder(int indexOrder, int newPrice) {
        if (newPrice<=0) {
            logger.info("Cost is below zero");
        }
        else {
            services = serviceDao.allServices();
            Service service = services.get(indexOrder);
            service.setPrice(newPrice);
            services.set(indexOrder, service);
        }
    }

    @Override
    public Service viewService(int indexService) {
        services = serviceDao.allServices();
        if (indexService<services.size()){
            System.out.println(services.get(indexService));
            return services.get(indexService);
        }
        else
            System.out.println("Такой услуги не существует");
        return null;
    }
}
