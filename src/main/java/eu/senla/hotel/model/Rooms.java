package eu.senla.hotel.model;

import javax.xml.bind.annotation.*;
import java.util.List;


@XmlRootElement(name = "roomService")
@XmlAccessorType(XmlAccessType.FIELD)
public class Rooms {

    @XmlElement(name = "Rooms")
    private List<Room> roomsList;

    public List<Room> getRooms() {
        return roomsList;
    }

    public void setRooms(List<Room> rooms) {
        this.roomsList = rooms;
    }
}
