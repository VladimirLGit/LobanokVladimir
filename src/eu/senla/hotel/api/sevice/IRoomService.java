package eu.senla.hotel.api.sevice;

import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.Room;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IRoomService {
    void checkIn(Guest guest);
    void checkOut(Guest guest);
    void listNumber();
    ArrayList<Room> listFreeRooms();
    ArrayList<Room> listCheckedRooms();
    int amountFreeRooms();
    ArrayList<Room> listFreeRoomsForDate(LocalDate date);
    void viewRoom(int indexRoom);
}
