package eu.senla.hotel.api.sevice;

import eu.senla.hotel.exception.NoFreeRoomInTheHotel;
import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.Room;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IRoomService {
    void addRoom(Room room);
    void deleteRoom(Room room);
    void checkIn(Guest guest);
    void checkOut(Guest guest);
    void listNumber();
    ArrayList<Room> listFreeRooms() throws NoFreeRoomInTheHotel;
    ArrayList<Room> listCheckedRooms();
    int amountFreeRooms();
    ArrayList<Room> listFreeRoomsForDate(LocalDate date);
    Room viewRoom(int indexRoom);
}
