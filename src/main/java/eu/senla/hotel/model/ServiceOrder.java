package eu.senla.hotel.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlType(propOrder={
        "id",
        "name",
        "price"})
@XmlRootElement(name = "service")
@Entity(name = "Services")
public class ServiceOrder implements Serializable {
    @Id
    @Column(name="idService")
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private int price;


    public ServiceOrder(String nameService, int priceService) {
        this.name = nameService;
        this.price = priceService;
    }

    public ServiceOrder() {
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
