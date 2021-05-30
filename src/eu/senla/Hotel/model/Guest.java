package eu.senla.Hotel.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Guest {
    private String nameGuest;
    private LocalDate dateOfCheckIn;
    private LocalDate dateOfCheckOut;
    private StateGuest stateGuest;
    private Room room;
    private ArrayList<Service> orderedServices;

    public Guest(String nameGuest) {
        this.nameGuest = nameGuest;
        ArrayList<Service> orderedServices = new ArrayList<>();
    }

    public void addOrderedService(Service service){
        orderedServices.add(service);
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

    public StateGuest getStateGuest() {
        return stateGuest;
    }

    public void setStateGuest(StateGuest stateGuest) {
        this.stateGuest = stateGuest;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
