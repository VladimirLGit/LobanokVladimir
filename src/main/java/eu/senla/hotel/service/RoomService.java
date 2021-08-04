package eu.senla.hotel.service;

import eu.senla.hotel.api.dao.IGuestDao;
import eu.senla.hotel.api.dao.IRoomDao;
import eu.senla.hotel.api.sevice.IRoomService;
import eu.senla.hotel.dao.RoomDao;
import eu.senla.hotel.dao.collection.LRoomDao;
import eu.senla.hotel.exception.NoFreeRoomInTheHotel;
import eu.senla.hotel.exception.NotExistObject;
import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.Room;
import eu.senla.hotel.model.Rooms;
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


public class RoomService implements IRoomService {
    public static final Logger logger = Logger.getLogger(
            RoomService.class.getName());

    private IRoomDao roomDao;

    private Rooms roomObjects;

    public RoomService() {
        roomObjects = new Rooms();
        roomDao = null;
    }

    public RoomService(IRoomDao ds) {
        this();
        roomDao = ds;
        roomObjects.setRooms(roomDao.allRooms());
    }

    @Override
    public Rooms getRoomObjects() {
        return roomObjects;
    }

    public void setRoomObjects(Rooms roomObjects) {
        this.roomObjects = roomObjects;
    }

    public void reloadDao(IRoomDao ds) {
        roomDao = ds;
    }

    @Override
    public void addRoom(Room room) {
        roomDao.addRoom(room);
        //rooms.add(room);
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
            //roomDao.addLinkGuestWithRoom(guest, room);
            guest.setIdRoom(room.getId()); //поселили гостя в комнату без критериев
        } catch (NoFreeRoomInTheHotel | NotExistObject noFreeRoomInTheHotel) {
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
        roomObjects.setRooms(roomDao.allRooms());
        int indexRoom = roomObjects.getRooms().indexOf(room);
        if (indexRoom >= 0) {
            room = roomObjects.getRooms().get(indexRoom);
            room.setRating(RANDOM.nextInt(5));
            room.setState(StateRoom.FREE);
            room.deleteGuest(guest);
            try {
                roomDao.updateRoom(room);
            } catch (NotExistObject notExistObject) {
                logger.info(notExistObject.getMessage());
            }
            guest.setIdRoom(0);
            guest.setDateOfCheckOut(today);
            //сумма к уплате за проживание и оказанные услуги
            priceRoom = room.getPrice();
            amountOfDaysOfStay = (int) DAYS.between(guest.getDateOfCheckIn(), guest.getDateOfCheckOut()) + 1;
        }


        System.out.println("К оплате = " + amountOfDaysOfStay * priceRoom + "$ за проживание");
    }

    public List<Room> getRooms() {
        if (roomDao == null)
            return roomObjects.getRooms();
        roomObjects.setRooms(roomDao.allRooms());
        if (roomObjects.getRooms().size()==0) {
            logger.info("no guests at the hotel");
        }
        return roomObjects.getRooms();
    }

    public void setRoomsList(List<Room> roomsList) {
        this.roomObjects.setRooms(roomsList);
    }

    @Override
    public void listNumber() {
        roomObjects.setRooms(roomDao.allRooms());
        for (Room room : roomObjects.getRooms()) {
            System.out.println(room);
        }
    }

    public void changePriceRoom(int newPrice, Room room) {
        if (newPrice <= 0) {
            logger.info("Cost is below zero");
        } else {
            System.out.println("До " + room);
            room.setPrice(newPrice);
            try {
                roomDao.updateRoom(room);
            } catch (NotExistObject notExistObject) {
                logger.info(notExistObject.getMessage());
            }
            System.out.println("После измениения цены");
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
        roomObjects.setRooms(roomDao.allRooms());
        roomObjects.getRooms().forEach(room -> addStateRoom(room, StateRoom.FREE, freeRooms));
        if (freeRooms.size() == 0) {
            throw new NoFreeRoomInTheHotel("No free room in the hotel");
        } else
            return freeRooms;
    }

    @Override
    public List<Room> listCheckedRooms() {
        List<Room> checkedRooms = new ArrayList<>();
        roomObjects.setRooms(roomDao.allRooms());
        roomObjects.getRooms().forEach(room -> addStateRoom(room, StateRoom.CHECKED, checkedRooms));
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
        roomObjects.setRooms(roomDao.allRooms());
        roomObjects.getRooms().forEach(room -> addStateRoom(room, StateRoom.FREE, date, freeRooms));
        return freeRooms;
    }

    @Override
    public Room viewRoom(int indexRoom) {
        roomObjects.setRooms(roomDao.allRooms());
        if (indexRoom < roomObjects.getRooms().size()) {
            System.out.println(roomObjects.getRooms().get(indexRoom));
            return roomObjects.getRooms().get(indexRoom);
        } else
            System.out.println("Номер не существует");
        return null;
    }

    public void changeStateRoom(StateRoom state, Room room) {
        System.out.println("До " + room);
        room.setState(state);
        try {
            roomDao.updateRoom(room);
        } catch (NotExistObject notExistObject) {
            logger.info(notExistObject.getMessage());
        }
        System.out.println("После измениения статуса");
        System.out.println(room);
    }
}
