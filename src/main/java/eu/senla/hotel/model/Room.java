package eu.senla.hotel.model;

import eu.senla.hotel.utils.guest.LocalDateAdapter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlType(propOrder = {
        "id",
        "number",
        "price",
        "rating",
        "numberOfGuests",
        "state",
        "type",
        "lastDayOfStay",
        "guests"})
@XmlRootElement(name = "room")
@javax.persistence.Entity(name = "Rooms")
@javax.persistence.Table(name = "Rooms")
public class Room implements java.io.Serializable {
    @Id
    @Column(name="idRoom")
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private Integer id;
    @Column(name="number")
    private int number;
    @Column(name="price")
    private int price;
    @Column(name="rating")
    private double rating;
    @Column(name="numberOfGuests")
    private int numberOfGuests;
    @ElementCollection
    @CollectionTable(
            name="guests",
            joinColumns=@JoinColumn(name="idGuest")
    )
    private List<Integer> guests;
    @Column(name="stateRoom")
    private StateRoom state;
    @Column(name="typeRoom")
    private TypeRoom type;
    @Column(name="lastDayOfStay")
    private LocalDate lastDayOfStay;

    public Room() {
    }

    public Room(int number, int price, int numberOfGuests, TypeRoom typeRoom) {
        this.number = number;
        this.price = price;
        this.numberOfGuests = numberOfGuests;
        this.type = typeRoom;
        this.state = StateRoom.FREE;
        this.lastDayOfStay = LocalDate.of(2000,1,1);
        guests = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer idRoom) {
        this.id = idRoom;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public List<Integer> getGuests() {
        return guests;
    }

    public void setGuests(List<Integer> guests) {
        this.guests = guests;
    }

    @XmlJavaTypeAdapter(type = LocalDate.class, value = LocalDateAdapter.class)
    public LocalDate getLastDayOfStay() {
        return lastDayOfStay;
    }

    public void setLastDayOfStay(LocalDate lastDayOfStay) {
        if (this.lastDayOfStay.isAfter(lastDayOfStay)) {
            this.lastDayOfStay = lastDayOfStay;
        }
    }

    public StateRoom getState() {
        return state;
    }

    public void setState(StateRoom stateRoom) {
        this.state = stateRoom;
    }

    public void addGuest(Guest guest) {
        LocalDate localDate = guest.getDateOfCheckOut();
        if ((localDate != null) && (localDate.isAfter(lastDayOfStay))) {
            lastDayOfStay = localDate;
        }
        guests.add(guest.getId());
    }

    public void deleteGuest(Guest guest) {
        int index = guests.indexOf(guest.getId());
        if (index != -1) guests.remove(index);
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
        return id.equals(room.id) && number == room.number && numberOfGuests == room.numberOfGuests && state == room.state && type == room.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, numberOfGuests, state, type);
    }
}
