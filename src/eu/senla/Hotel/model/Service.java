package eu.senla.hotel.model;

public class Service {
    private int id;
    private String name;
    private int price;
    public Service(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public int getIdService() {
        return id;
    }

    public void setIdService(int idService) {
        this.id = idService;
    }

    public String getNameService() {
        return name;
    }

    public void setNameService(String nameService) {
        this.name = nameService;
    }

    public int getPriceService() {
        return price;
    }

    public void setPriceService(int priceService) {
        this.price = priceService;
    }

    @Override
    public String toString() {
        return "Service{" +
                "nameService='" + name + '\'' +
                ", priceService=" + price +
                '}';
    }
}
