package eu.senla.hotel.model.links;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity(name = "linkTable")
public class ItemTable implements Serializable {
    @Id
    @Column(name="idRoom")
    private Integer idRoom;
    @Column(name="idGuest")
    private Integer idGuest;
}
