package eu.senla.Hotel.model;

import java.util.ArrayList;

public class Room {
    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    private int idRoom;
    private int number;
    private int price;
    private double rating;
    private int numberOfGuests;
    private ArrayList<Guest> guests;
    private StateRoom stateRoom;
    private TypeRoom typeRoom;
    public Room(int number, int price, int numberOfGuests, TypeRoom typeRoom) {
        this.number = number;
        this.price = price;
        this.numberOfGuests = numberOfGuests;
        this.typeRoom = typeRoom;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public int getNumber() {
        return number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public TypeRoom getTypeRoom() {
        return typeRoom;
    }

    public void setTypeRoom(TypeRoom typeRoom) {
        this.typeRoom = typeRoom;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public ArrayList<Guest> getGuests() {
        return guests;
    }


    public void setGuests(ArrayList<Guest> guests) {
        this.guests = guests;
    }

    public StateRoom getStateRoom() {
        return stateRoom;
    }

    public void setStateRoom(StateRoom stateRoom) {
        this.stateRoom = stateRoom;
    }

    @Override
    public String toString() {
        return "Room{" +
                "number=" + number +
                ", price=" + price +
                ", rating=" + rating +
                ", numberOfGuests=" + numberOfGuests +
                ", stateRoom=" + stateRoom +
                ", typeRoom=" + typeRoom +
                '}';
    }
}
