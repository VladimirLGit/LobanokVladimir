package eu.senla.hotel.service;

import eu.senla.hotel.api.sevice.IRoomService;
import eu.senla.hotel.dao.RoomDao;
import eu.senla.hotel.exception.NoFreeRoomInTheHotel;
import eu.senla.hotel.exception.NotExistObject;
import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.Room;
import eu.senla.hotel.model.StateRoom;

import javax.sql.DataSource;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@XmlType(name = "roomService")
@XmlRootElement
public class RoomService implements IRoomService {
    public static final Logger logger = Logger.getLogger(
            RoomService.class.getName());

    private final RoomDao roomDao;
    @XmlElementWrapper(name = "roomList")
    @XmlElement(name = "Rooms")
    private List<Room> rooms;

    public RoomService(DataSource ds) {
        roomDao = new RoomDao(ds);
        rooms = new ArrayList<>(); //roomDao.allRooms();
    }

    @Override
    public void addRoom(Room room) {
        roomDao.addRoom(room);
        rooms.add(room);
    }

    @Override
    public void deleteRoom(Room room) {
        if (room != null) {
            try {
                roomDao.deleteRoom(room);
            } catch (NotExistObject notExistObject) {
                logger.info("The room does not exist"); //notExistObject.printStackTrace();
                logger.log(Level.ALL, "The room does not exist");
            }
        }
    }

    @Override
    public void checkIn(Guest guest) {
        Random RANDOM = new Random();
        List<Room> freeRooms;
        try {
            freeRooms = listFreeRooms();
            Room room = freeRooms.get(RANDOM.nextInt(freeRooms.size()));
            room.setState(StateRoom.CHECKED);
            room.addGuest(guest);
            room.setLastDayOfStay(guest.getDateOfCheckOut());
            roomDao.updateRoom(room);
            roomDao.addLinkGuestWithRoom(guest, room);
            guest.setIdRoom(room.getId()); //???????????????? ?????????? ?? ?????????????? ?????? ??????????????????
        } catch (NoFreeRoomInTheHotel noFreeRoomInTheHotel) {
            logger.info(noFreeRoomInTheHotel.getMessage());
        }

    }


    @Override
    public void checkOut(Guest guest) {
        Random RANDOM = new Random();
        LocalDate today = LocalDate.now();
        int idRoom = guest.getIdRoom();
        int amountOfDaysOfStay = 0;
        int priceRoom = 0;
        Room room = roomDao.getRoomForId(idRoom);
        int indexRoom = rooms.indexOf(room);
        if (indexRoom >= 0) {
            room = rooms.get(indexRoom);
            room.setRating(RANDOM.nextInt(5));
            room.setState(StateRoom.FREE);
            room.deleteGuest(guest);
            roomDao.updateRoom(room);
            guest.setIdRoom(0);
            guest.setDateOfCheckOut(today);
            //?????????? ?? ???????????? ???? ???????????????????? ?? ?????????????????? ????????????
            priceRoom = room.getPrice();
            amountOfDaysOfStay = (int) DAYS.between(guest.getDateOfCheckIn(), guest.getDateOfCheckOut()) + 1;
        }


        System.out.println("?? ???????????? = " + amountOfDaysOfStay * priceRoom + "$ ???? ????????????????????");
    }

    public List<Room> getRooms() {
        if (rooms.size()==0) {
            logger.info("no guests at the hotel");
        }
        return rooms;
    }

    public void setRoomsList(List<Room> roomsList) {
        this.rooms = roomsList;
    }

    @Override
    public void listNumber() {
        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    public void changePriceRoom(int newPrice, Room room) {
        if (newPrice <= 0) {
            logger.info("Cost is below zero");
        } else {
            System.out.println("???? " + room);
            room.setPrice(newPrice);
            roomDao.updateRoom(room);
            System.out.println("?????????? ???????????????????? ????????");
            System.out.println(room);
        }
    }

    void addStateRoom(Room room, StateRoom stateRoom, List<Room> list) {
        if (room.getState() == stateRoom) {
            list.add(room);
        }
    }

    void addStateRoom(Room room, StateRoom state, LocalDate date, List<Room> list) {
        List<Integer> guests;
        LocalDate dateCheckOut = null;
        if (room.getState() == state) {
            list.add(room);
        } else if (room.getState() == StateRoom.CHECKED) {

            dateCheckOut = room.getLastDayOfStay();
            if ((dateCheckOut != null) && (dateCheckOut.equals(date) || dateCheckOut.isBefore(date))) {
                list.add(room);
            }
        }
    }

    @Override
    public List<Room> listFreeRooms() throws NoFreeRoomInTheHotel {
        List<Room> freeRooms = new ArrayList<>();
        rooms.forEach(room -> addStateRoom(room, StateRoom.FREE, freeRooms));
        if (freeRooms.size() == 0) {
            throw new NoFreeRoomInTheHotel("No free room in the hotel");
        } else
            return freeRooms;
    }

    @Override
    public List<Room> listCheckedRooms() {
        List<Room> checkedRooms = new ArrayList<>();
        rooms.forEach(room -> addStateRoom(room, StateRoom.CHECKED, checkedRooms));
        return checkedRooms;
    }

    @Override
    public int amountFreeRooms() {
        try {
            List<Room> rooms = listFreeRooms();
            return rooms.size();
        } catch (NoFreeRoomInTheHotel noFreeRoomInTheHotel) {
            logger.info(noFreeRoomInTheHotel.getMessage());
        }
        return 0;
    }

    @Override
    public List<Room> listFreeRoomsForDate(LocalDate date) {
        List<Room> freeRooms = new ArrayList<>();
        rooms.forEach(room -> addStateRoom(room, StateRoom.FREE, date, freeRooms));
        return freeRooms;
    }

    @Override
    public Room viewRoom(int indexRoom) {
        if (indexRoom < rooms.size()) {
            System.out.println(rooms.get(indexRoom));
            return rooms.get(indexRoom);
        } else
            System.out.println("?????????? ???? ????????????????????");
        return null;
    }

    public void changeStateRoom(StateRoom state, Room room) {
        System.out.println("???? " + room);
        room.setState(state);
        roomDao.updateRoom(room);
        System.out.println("?????????? ???????????????????? ??????????????");
        System.out.println(room);
    }
}
