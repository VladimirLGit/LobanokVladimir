package eu.senla.hotel.api.sevice;

import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.Room;

import java.time.LocalDate;
import java.util.List;

public interface IRoomService {
    void addRoom(Room room);
    void deleteRoom(Room room);
    void checkIn(Guest guest);
    void checkOut(Guest guest);
    void listNumber();
    List<Room> listFreeRooms();
    List<Room> listCheckedRooms();
    int amountFreeRooms();
    List<Room> listFreeRoomsForDate(LocalDate date);
    Room viewRoom(int indexRoom);
}
