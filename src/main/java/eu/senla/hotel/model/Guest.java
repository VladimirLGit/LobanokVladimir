package eu.senla.hotel.model;

import eu.senla.hotel.utils.guest.LocalDateAdapter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlType(propOrder = {
        "id",
        "name",
        "dateOfCheckIn",
        "dateOfCheckOut",
        "state",
        "idRoom",
        "orderedServices"})
@XmlRootElement(name = "guest")

@Entity(name = "Guests")
public class Guest implements Serializable {
    @Id
    @Column(name="idGuest")
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "DateOfCheckIn")
    private LocalDate dateOfCheckIn;
    @Column(name = "DateOfCheckOut")
    private LocalDate dateOfCheckOut;
    @Column(name = "StateGuest")
    private StateGuest state;
    //@ManyToOne
    //@JoinColumn(name="idRoom")
    @ManyToOne (optional=false, cascade=CascadeType.ALL)
    @JoinColumn (name="idRoom")
    private Room room;

    @XmlElementWrapper(name = "orderedServices")
    @XmlElement(name = "service")
    //@OneToMany(targetEntity = Service.class, mappedBy = "guest",
    //        cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //@OneToMany
    //@JoinColumn(name = "idGuest")
    //@OneToMany(fetch = FetchType.LAZY,
    //        mappedBy = "guest",
    //        cascade = CascadeType.ALL)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private List<ServiceOrder> orderedServiceOrders;

    public Guest() {
    }

    public Guest(String nameGuest) {
        this.name = nameGuest;
        this.room = new Room(0, 0,0, TypeRoom.STANDARD);
        this.state = StateGuest.NO_STATE;
        orderedServiceOrders = new ArrayList<>();
    }

    public void clearListOrder() {
        orderedServiceOrders.clear();
    }
    /*
    public void addOrderedService(Service service) {
        orderedServices.add(service.getId());
    }

    public List<Integer> getOrderedServices() {
        return orderedServices;
    }

    public void setListServices(List<Integer> orderedServices) {
        this.orderedServices = orderedServices;
    }
    */
    public void addOrderedService(ServiceOrder serviceOrder) {
        orderedServiceOrders.add(serviceOrder);
    }

    public List<ServiceOrder> getOrderedServices() {
        return orderedServiceOrders;
    }

    public void setListServices(List<ServiceOrder> orderedServiceOrders) {
        this.orderedServiceOrders = orderedServiceOrders;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlJavaTypeAdapter(type = LocalDate.class, value = LocalDateAdapter.class)
    public LocalDate getDateOfCheckIn() {
        return dateOfCheckIn;
    }

    public void setDateOfCheckIn(LocalDate dateOfCheckIn) {
        this.dateOfCheckIn = dateOfCheckIn;
    }

    @XmlJavaTypeAdapter(type = LocalDate.class, value = LocalDateAdapter.class)
    public LocalDate getDateOfCheckOut() {
        return dateOfCheckOut;
    }

    public void setDateOfCheckOut(LocalDate dateOfCheckOut) {
        this.dateOfCheckOut = dateOfCheckOut;
    }

    public StateGuest getState() {
        return state;
    }

    public void setState(StateGuest stateGuest) {
        this.state = stateGuest;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "Guest{" +
                "name='" + name + '\'' +
                ", dateOfCheckIn=" + dateOfCheckIn +
                ", dateOfCheckOut=" + dateOfCheckOut +
                ", state=" + state +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return id.equals(guest.id) && Objects.equals(name, guest.name) && Objects.equals(dateOfCheckIn, guest.dateOfCheckIn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dateOfCheckIn);
    }
}
