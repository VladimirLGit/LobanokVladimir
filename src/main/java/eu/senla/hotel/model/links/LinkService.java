package eu.senla.hotel.model.links;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity(name = "linkService")
public class LinkService implements Serializable {
    @Id
    @Column(name="idService")
    private Integer idService;
    @Column(name="idGuest")
    private Integer idGuest;

    public LinkService(Integer idService, Integer idGuest) {
        this.idService = idService;
        this.idGuest = idGuest;
    }
}
