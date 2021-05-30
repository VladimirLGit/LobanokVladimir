package eu.senla.Hotel.model;

import java.util.ArrayList;

public class Room {
    private int number;
    private int price;
    private double rating;
    private int numberOfGuests;
    private ArrayList<Guest> guests;
    private StateRoom stateRoom;
    private TypeRoom typeRoom;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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
