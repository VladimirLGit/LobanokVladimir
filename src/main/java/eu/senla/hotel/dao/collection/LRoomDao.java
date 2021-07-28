package eu.senla.hotel.dao.collection;

import eu.senla.hotel.api.dao.IRoomDao;
import eu.senla.hotel.exception.NotExistObject;
import eu.senla.hotel.model.Room;

import java.util.ArrayList;
import java.util.List;

public class LRoomDao implements IRoomDao {
    private List<Room> rooms;

    public LRoomDao() {
        rooms = new ArrayList<>();
    }

    @Override
    public void addRoom(Room room) {
        rooms.add(room);
    }

    @Override
    public void deleteRoom(Room room) throws NotExistObject {
        if (!rooms.remove(room))
            throw new NotExistObject("Not exist the room " + room.getNumber());
    }

    @Override
    public void updateRoom(Room room) throws NotExistObject {
        int i = rooms.indexOf(room);
        if (i!=-1) {
            rooms.set(i, room);
        }
        else
            throw new NotExistObject("Not exist the room " + room.getNumber());
    }

    @Override
    public List<Room> allRooms() {
        return rooms;
    }

    @Override
    public Room checkGuest(int id) {
        return null;
    }
}
