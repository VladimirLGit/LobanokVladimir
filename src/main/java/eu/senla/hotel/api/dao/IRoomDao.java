package eu.senla.hotel.api.dao;

import eu.senla.hotel.model.Room;
import java.util.List;

public interface IRoomDao {
    void addRoom(Room room);
    void deleteRoom(Room room);
    void updateRoom(Room room);
    List<Room> allRooms();
}
