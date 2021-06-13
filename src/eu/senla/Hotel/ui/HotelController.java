package eu.senla.Hotel.ui;

import eu.senla.Hotel.dao.GuestDao;
import eu.senla.Hotel.dao.RoomDao;
import eu.senla.Hotel.dao.ServiceDao;
import eu.senla.Hotel.model.Room;
import eu.senla.Hotel.model.TypeRoom;
import eu.senla.Hotel.service.GuestService;
import eu.senla.Hotel.service.HotelService;
import eu.senla.Hotel.service.RoomService;

import java.util.Random;

public class HotelController {
    private String[] listNameGuests = new String[]{
            "Tom",
            "Jack",
            "Rose",
            "Alice",
            "Oscar",
            "Leo",
            "John"};
    private GuestDao guestDao;
    private RoomDao roomDao;
    private ServiceDao serviceDao;
    private GuestService guestService;
    private RoomService roomService;
    private HotelService hotelService;

    private void createDao(){
        guestDao = new GuestDao();
        roomDao = new RoomDao();
        serviceDao = new ServiceDao();
    }

    public void setUp() {
        createDao();
        guestDao.createHotelBase();
        guestDao.createTableGuests();
        guestDao.createLinkTableServices();
        guestDao.createTableHistoryGuests();

        roomDao.createTableRooms();
        roomDao.createLinkTableRooms();
        serviceDao.createTableServices();
        guestService = new GuestService();
        roomService = new RoomService();
        hotelService = new HotelService();
    }

    public void addRoom() {
        Random RANDOM = new Random();
        Room room = new Room(RANDOM.nextInt(100),
                RANDOM.nextInt(15)*10,
                RANDOM.nextInt(5)+1,
                TypeRoom.values()[RANDOM.nextInt(TypeRoom.values().length)]);
        roomDao.addRoom(room);
    }
    public void deleteRoom() {

    }


    public void addService() {

    }
    public void deleteService() {

    }

    public void addGuest() {

    }
    public void deleteGuest() {

    }


}
