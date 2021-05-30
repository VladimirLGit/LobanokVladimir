package eu.senla.Hotel.api.sevice;

public interface IServiceService {
    void order(int indexOrder);
    void listOrder();
    void changePriceOrder(int indexOrder, int newPrice);
}
