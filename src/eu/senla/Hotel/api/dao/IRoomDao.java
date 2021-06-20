package eu.senla.Hotel.api.dao;

import eu.senla.Hotel.exception.NotExistObject;
import eu.senla.Hotel.model.Room;

import java.util.ArrayList;

public interface IRoomDao {
    void addRoom(Room room);
    void deleteRoom(Room room) throws NotExistObject;
    void updateRoom(Room room);
    ArrayList<Room> allRooms();
}
