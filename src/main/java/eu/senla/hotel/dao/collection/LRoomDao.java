package eu.senla.hotel.dao.collection;

import eu.senla.hotel.api.dao.IRoomDao;
import eu.senla.hotel.exception.NotExistObject;
import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.Room;

import java.util.ArrayList;
import java.util.List;

public class LRoomDao implements IRoomDao {
    private List<Room> rooms;

    public LRoomDao() {
        rooms = new ArrayList<>();
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public void addRoom(Room room) {
        int newId = rooms.stream()
                .mapToInt(Room::getId)
                .summaryStatistics()
                .getMax();
        room.setId(newId + 1);
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
            room.setId(rooms.get(i).getId());
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

    public Room getRoomForId(int id) {
        for (Room room : rooms) {
            if (room.getId() == id)
                return room;
        }
        return null;
    }
}
