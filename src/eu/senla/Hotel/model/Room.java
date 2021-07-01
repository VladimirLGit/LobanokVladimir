package eu.senla.hotel.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Room {
    private int id;
    private int number;
    private int price;
    private double rating;
    private int numberOfGuests;
    private List<Guest> guests;
    private StateRoom state;
    private TypeRoom typeRoom;

    public Room(int number, int price, int numberOfGuests, TypeRoom typeRoom) {
        this.number = number;
        this.price = price;
        this.numberOfGuests = numberOfGuests;
        this.typeRoom = typeRoom;
        this.state = StateRoom.FREE;
        guests = new ArrayList<>();
    }
    public int getIdRoom() {
        return id;
    }
    public void setIdRoom(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
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

    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(ArrayList<Guest> guests) {
        this.guests = guests;
    }

    public void addGuest(Guest guest){
        guests.add(guest);
    }

    public void deleteGuest(Guest guest){
        int index = guests.indexOf(guest);
        if (index!=-1) guests.remove(index);
    }

    public StateRoom getStateRoom() {
        return state;
    }

    public void setStateRoom(StateRoom stateRoom) {
        this.state = stateRoom;
    }

    @Override
    public String toString() {
        return "Room{" +
                "number=" + number +
                ", price=" + price +
                ", rating=" + rating +
                ", numberOfGuests=" + numberOfGuests +
                ", stateRoom=" + state +
                ", typeRoom=" + typeRoom +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id && number == room.number && numberOfGuests == room.numberOfGuests && state == room.state && typeRoom == room.typeRoom;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, numberOfGuests, state, typeRoom);
    }
}
