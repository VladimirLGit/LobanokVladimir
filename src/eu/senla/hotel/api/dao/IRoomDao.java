package eu.senla.hotel.api.dao;

import eu.senla.hotel.exception.NotExistObject;
import eu.senla.hotel.model.Room;

import java.util.ArrayList;

public interface IRoomDao {
    void addRoom(Room room);
    void deleteRoom(Room room) throws NotExistObject;
    void updateRoom(Room room);
    ArrayList<Room> allRooms();
}
