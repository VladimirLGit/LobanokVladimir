package eu.senla.hotel.api.sevice;

public interface IServiceService {
    void order(int indexOrder);
    void listOrder();
    void changePriceOrder(int indexOrder, int newPrice);
}
