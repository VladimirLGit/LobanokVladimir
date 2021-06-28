package eu.senla.Hotel.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;



@XmlRootElement(name = "guest")
/*
@XmlType(propOrder={
        "id",
        "name",
        "dateOfCheckIn",
        "dateOfCheckOut",
        "state",
        "room",
        "orderedServices"})
*/
public class Guest {

    private int id;
    private String name;
    private LocalDate dateOfCheckIn;
    private LocalDate dateOfCheckOut;
    private StateGuest state;
    private Room room;
    @XmlElementWrapper(name = "orderedServices")
    @XmlElement(name = "Service")
    private ArrayList<Service> orderedServices;

    public Guest() {
    }

    public Guest(String nameGuest) {
        this.name = nameGuest;
        this.room = null;
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
    public void setId(int idGuest) {
        this.id = idGuest;
    }

    public LocalDate getDateOfCheckIn() {
        return dateOfCheckIn;
    }
    public void setDateOfCheckIn(LocalDate dateOfCheckIn) {
        this.dateOfCheckIn = dateOfCheckIn;
    }

    public LocalDate getDateOfCheckOut() {
        return dateOfCheckOut;
    }
    public void setDateOfCheckOut(LocalDate dateOfCheckOut) {
        this.dateOfCheckOut = dateOfCheckOut;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
        return id == guest.id && Objects.equals(name, guest.name) && Objects.equals(dateOfCheckIn, guest.dateOfCheckIn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dateOfCheckIn);
    }
}
