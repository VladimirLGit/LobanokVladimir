package eu.senla.hotel.api.dao;

import eu.senla.hotel.exception.NotExistObject;
import eu.senla.hotel.model.Room;

import java.util.ArrayList;
import java.util.List;

public interface IRoomDao {
    void addRoom(Room room);
    void deleteRoom(Room room) throws NotExistObject;
    void updateRoom(Room room) throws NotExistObject;
    List<Room> allRooms();
    Room checkGuest(int id);
    Room getRoomForId(int idRoom);
    void setRooms(List<Room> rooms);
}
