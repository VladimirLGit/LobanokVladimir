package eu.senla.Hotel.api.sevice;

import eu.senla.Hotel.model.Guest;
import eu.senla.Hotel.model.Room;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public interface IRoomService {
    void addRoom(Room room);
    void deleteRoom(Room room);
    void checkIn(Guest guest);
    void checkOut(Guest guest);
    void listNumber();
    ArrayList<Room> listFreeRooms();
    ArrayList<Room> listCheckedRooms();
    int amountFreeRooms();
    ArrayList<Room> listFreeRoomsForDate(LocalDate date);
    Room viewRoom(int indexRoom);
}
