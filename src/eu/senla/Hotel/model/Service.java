package eu.senla.Hotel.model;

import javax.xml.bind.annotation.*;

@XmlType(propOrder={
        "id",
        "name",
        "price"})
@XmlRootElement(name = "service")
public class Service {
    private int id;
    private String name;
    private int price;

    public Service(String nameService, int priceService) {
        this.name = nameService;
        this.price = priceService;
    }

    public Service() {
    }

    public int getId() {
        return id;
    }

    public void setId(int idService) {
        this.id = idService;
    }

    public String getName() {
        return name;
    }

    public void setName(String nameService) {
        this.name = nameService;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int priceService) {
        this.price = priceService;
    }

    @Override
    public String toString() {
        return "Service{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
