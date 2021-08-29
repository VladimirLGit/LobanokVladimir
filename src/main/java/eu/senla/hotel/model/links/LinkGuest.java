package eu.senla.hotel.model.links;

import eu.senla.hotel.model.StateGuest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;

@Entity(name="historyGuests")
public class LinkGuest implements Serializable {
    @Id
    @Column(name = "idGuest")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "DateOfCheckIn")
    private LocalDate dateOfCheckIn;
    @Column(name = "DateOfCheckOut")
    private LocalDate dateOfCheckOut;
    @Column(name = "StateGuest")
    private StateGuest state;

    public LinkGuest(Integer id, String name, LocalDate dateOfCheckIn, LocalDate dateOfCheckOut, StateGuest state) {
        this.id = id;
        this.name = name;
        this.dateOfCheckIn = dateOfCheckIn;
        this.dateOfCheckOut = dateOfCheckOut;
        this.state = state;
    }
}
