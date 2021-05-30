package eu.senla.Hotel.service;

import eu.senla.Hotel.api.sevice.IServiceService;
import eu.senla.Hotel.model.Service;

import java.util.ArrayList;

public class HotelService implements IServiceService {
    private ArrayList<Service> services;
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
