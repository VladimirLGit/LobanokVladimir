package eu.senla.hotel.model;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

@XmlType(propOrder={
        "id",
        "name",
        "price"})
@XmlRootElement(name = "service")
@Entity
@Table(name = "Services")
@Embeddable
public class Service implements java.io.Serializable {
    @Id
    @Column(name="idService")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private int price;

    public Service(String nameService, int priceService) {
        this.name = nameService;
        this.price = priceService;
    }

    public Service() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer idService) {
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
