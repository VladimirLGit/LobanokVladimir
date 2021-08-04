package eu.senla.hotel.model;

import javax.xml.bind.annotation.*;
import java.util.List;


@XmlRootElement(name = "guestService")
@XmlAccessorType(XmlAccessType.FIELD)
public class Guests {

    // XmlElement sets the name of the entities
    @XmlElement(name = "Guests")
    private List<Guest> guestsList;

    public List<Guest> getGuestsList() {
        return guestsList;
    }

    public void setGuestsList(List<Guest> guests) {
        this.guestsList = guests;
    }
}
