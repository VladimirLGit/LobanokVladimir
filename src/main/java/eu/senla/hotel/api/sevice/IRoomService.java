package eu.senla.hotel.api.sevice;

import eu.senla.hotel.api.dao.IRoomDao;
import eu.senla.hotel.exception.NoFreeRoomInTheHotel;
import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.Room;
import eu.senla.hotel.model.StateRoom;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface IRoomService {
    void addRoom(Room room);
    void deleteRoom(Room room);
    void checkIn(Guest guest);
    void checkOut(Guest guest);
    void listNumber();
    List<Room> listFreeRooms() throws NoFreeRoomInTheHotel;
    List<Room> listCheckedRooms();
    int amountFreeRooms();
    List<Room> listFreeRoomsForDate(LocalDate date);
    Room viewRoom(int indexRoom);
    List<Room> getRooms();
    void changePriceRoom(int newPrice, Room room);
    void changeStateRoom(StateRoom stateRoom, Room room);
}
