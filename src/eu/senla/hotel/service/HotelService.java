package eu.senla.hotel.service;

import eu.senla.hotel.api.sevice.IServiceService;
import eu.senla.hotel.model.Service;

import java.util.List;

public class HotelService implements IServiceService {
    private List<Service> services;
    @Override
    public void order(int indexOrder) {
        System.out.println( services.get(indexOrder) + " service done");
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
