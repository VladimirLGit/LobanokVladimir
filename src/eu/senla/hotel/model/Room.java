package eu.senla.hotel.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Objects;

@XmlRootElement(name = "room")
public class Room {
    private int id;
    private int number;
    private int price;
    private double rating;
    private int numberOfGuests;
    private ArrayList<Guest> guests;
    private StateRoom state;
    private TypeRoom type;

    public Room() {
    }

    public Room(int number, int price, int numberOfGuests, TypeRoom typeRoom) {
        this.number = number;
        this.price = price;
        this.numberOfGuests = numberOfGuests;
        this.type = typeRoom;
        this.state = StateRoom.FREE;
        guests = new ArrayList<>();
    }

    public int getId() {
        return id;
    }
    public void setId(int idRoom) {
        this.id = idRoom;
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

    public TypeRoom getType() {
        return type;
    }

    public void setType(TypeRoom typeRoom) {
        this.type = typeRoom;
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

    public void addGuest(Guest guest){
        guests.add(guest);
    }

    public void deleteGuest(Guest guest){
        int index = guests.indexOf(guest);
        if (index!=-1) guests.remove(index);
    }

    public StateRoom getState() {
        return state;
    }

    public void setState(StateRoom stateRoom) {
        this.state = stateRoom;
    }

    @Override
    public String toString() {
        return "Room{" +
                "number=" + number +
                ", price=" + price +
                ", rating=" + rating +
                ", numberOfGuests=" + numberOfGuests +
                ", state=" + state +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id && number == room.number && numberOfGuests == room.numberOfGuests && state == room.state && type == room.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, numberOfGuests, state, type);
    }
}
