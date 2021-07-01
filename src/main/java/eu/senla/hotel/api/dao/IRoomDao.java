package main.java.eu.senla.hotel.api.dao;

import main.java.eu.senla.hotel.exception.NotExistObject;
import main.java.eu.senla.hotel.model.Room;

import java.util.ArrayList;
import java.util.List;

public interface IRoomDao {
    void addRoom(Room room);
    void deleteRoom(Room room) throws NotExistObject;
    void updateRoom(Room room);
    List<Room> allRooms();

    Room checkGuest(int id);
}
