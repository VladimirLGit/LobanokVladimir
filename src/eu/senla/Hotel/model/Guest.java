package eu.senla.Hotel.model;

import eu.senla.Hotel.utils.guest.LocalDateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;





@XmlType(propOrder={
        "id",
        "name",
        "dateOfCheckIn",
        "dateOfCheckOut",
        "state",
        "idRoom",
        "orderedServices"})

@XmlRootElement(name = "guest")
public class Guest {
    private int id;
    private String name;
    private LocalDate dateOfCheckIn;
    private LocalDate dateOfCheckOut;
    private StateGuest state;
    private int idRoom;
    @XmlElementWrapper(name = "orderedServices")
    @XmlElement(name="service")
    private ArrayList<Service> orderedServices;

    public Guest() {
    }

    public Guest(String nameGuest) {
        this.name = nameGuest;
        this.idRoom = 0;
        this.state = StateGuest.NO_STATE;
        orderedServices = new ArrayList<>();
    }

    public void clearListOrder(){
        orderedServices.clear();
    }
    public void addOrderedService(Service service){
        orderedServices.add(service);
    }
    public ArrayList<Service> getOrderedServices() {
        return orderedServices;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @XmlJavaTypeAdapter(type= LocalDate.class, value=LocalDateAdapter.class)
    public LocalDate getDateOfCheckIn() {
        return dateOfCheckIn;
    }
    public void setDateOfCheckIn(LocalDate dateOfCheckIn) {
        this.dateOfCheckIn = dateOfCheckIn;
    }
    @XmlJavaTypeAdapter(type= LocalDate.class, value=LocalDateAdapter.class)
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
    public int getIdRoom() {
        return idRoom;
    }
    public void setIdRoom(int idRoom) {
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
        return id == guest.id && Objects.equals(name, guest.name) && Objects.equals(dateOfCheckIn, guest.dateOfCheckIn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dateOfCheckIn);
    }
}
