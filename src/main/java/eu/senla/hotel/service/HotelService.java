package eu.senla.hotel.service;

import eu.senla.hotel.api.dao.IServiceDao;
import eu.senla.hotel.api.sevice.IServiceService;
import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.ServiceOrder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
    public void order(Guest guest, ServiceOrder serviceOrder) {
        System.out.println(serviceOrder + " service done");
        if ((guest != null) && (serviceOrder != null))
            serviceDao.addOrderGuest(guest, serviceOrder);
        else
           logger.info("guest is null or service is null");
    }

    public void addService(ServiceOrder serviceOrder) {
        serviceDao.addService(serviceOrder);
    }

    public void deleteService(ServiceOrder serviceOrder) {
        serviceDao.deleteService(serviceOrder);
    }

    public List<ServiceOrder> getServices() {
        return serviceDao.allServices();
    }

    public void setServices(List<ServiceOrder> serviceOrders) {
        if (serviceOrders != null)
            this.serviceDao.setServices(serviceOrders);
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
            ServiceOrder serviceOrder = serviceDao.allServices().get(indexOrder);
            serviceOrder.setPrice(newPrice);
            serviceDao.updateService(serviceOrder);
        }
    }

    @Override
    public ServiceOrder viewService(int indexService) {
        List<ServiceOrder> serviceOrders = serviceDao.allServices();
        if (indexService < serviceOrders.size()) {
            System.out.println(serviceOrders.get(indexService));
            return serviceOrders.get(indexService);
        } else
            System.out.println("Такой услуги не существует");
        return null;
    }
}
