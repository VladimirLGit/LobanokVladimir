package eu.senla.Hotel.api.dao;

import eu.senla.Hotel.model.Room;

import java.util.ArrayList;

public interface IRoomDao {
    void addRoom(Room room);
    void deleteRoom(Room room);
    void updateRoom(Room room);
    ArrayList<Room> allRooms();
}
