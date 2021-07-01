package eu.senla.hotel.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Guest {
    private int id;
    private String name;
    private LocalDate dateOfCheckIn;
    private LocalDate dateOfCheckOut;
    private StateGuest state;
    private Room room;
    private List<Service> orderedServices;

    public Guest(String name) {
        this.name = name;
        this.room = null;
        orderedServices = new ArrayList<>();
    }

    public void clearListOrder() {
        orderedServices.clear();
    }

    public void addOrderedService(Service service) {
        orderedServices.add(service);
    }

    public List<Service> getOrderedServices() {
        return orderedServices;
    }

    public int getIdGuest() {
        return id;
    }

    public void setIdGuest(int id) {
        this.id = id;
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

    public String getNameGuest() {
        return name;
    }

    public StateGuest getStateGuest() {
        return state;
    }

    public void setStateGuest(StateGuest state) {
        this.state = state;
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
                "nameGuest='" + name + '\'' +
                ", dateOfCheckIn=" + dateOfCheckIn +
                ", dateOfCheckOut=" + dateOfCheckOut +
                ", stateGuest=" + state +
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
