package eu.senla.Hotel.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Guest {
    private int idGuest;

    private String nameGuest;

    private LocalDate dateOfCheckIn;
    private LocalDate dateOfCheckOut;
    private StateGuest stateGuest;
    private Room room;
    private ArrayList<Service> orderedServices;

    public Guest(String nameGuest) {
        this.nameGuest = nameGuest;
        this.room = null;
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

    public int getIdGuest() {
        return idGuest;
    }

    public void setIdGuest(int idGuest) {
        this.idGuest = idGuest;
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
        return nameGuest;
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

    @Override
    public String toString() {
        return "Guest{" +
                "nameGuest='" + nameGuest + '\'' +
                ", dateOfCheckIn=" + dateOfCheckIn +
                ", dateOfCheckOut=" + dateOfCheckOut +
                ", stateGuest=" + stateGuest +
                '}';
    }
}
