package eu.senla.hotel.model;

import eu.senla.hotel.utils.guest.LocalDateAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
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
public class Guest {
    private Integer id;
    private String name;
    private LocalDate dateOfCheckIn;
    private LocalDate dateOfCheckOut;
    private StateGuest state;
    private Integer idRoom;

    @XmlElementWrapper(name = "orderedServices")
    @XmlElement(name = "service")
    private List<Integer> orderedServices;

    public Guest() {
    }

    public Guest(String nameGuest) {
        this.name = nameGuest;
        this.idRoom = null;
        this.state = StateGuest.NO_STATE;
        orderedServices = new ArrayList<>();
    }

    public void clearListOrder() {
        orderedServices.clear();
    }

    public void addOrderedService(Service service) {
        orderedServices.add(service.getId());
    }

    public List<Integer> getOrderedServices() {
        return orderedServices;
    }

    public void setListServices(List<Integer> orderedServices) {
        this.orderedServices = orderedServices;
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

    public Integer getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Integer idRoom) {
        this.idRoom = idRoom;
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
