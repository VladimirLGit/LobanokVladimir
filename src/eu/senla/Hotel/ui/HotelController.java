package eu.senla.hotel.ui;

import eu.senla.hotel.dao.GuestDao;
import eu.senla.hotel.dao.MainDao;
import eu.senla.hotel.dao.RoomDao;
import eu.senla.hotel.dao.ServiceDao;
import eu.senla.hotel.model.*;
import eu.senla.hotel.service.GuestService;
import eu.senla.hotel.service.HotelService;
import eu.senla.hotel.service.RoomService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    String[] listNameServices = new String[]{"Заказ еды в номер",
            "Уборка номера",
            "Массаж",
            "Вызов такси",
            "Чистка одежды",
            "Покупка сувениров",
            "Тренажерный зал"};
    private static HotelController instance;
    private MainDao mainDao;
    private GuestDao guestDao;
    private RoomDao roomDao;
    private ServiceDao serviceDao;
    private GuestService guestService;
    private RoomService roomService;
    private HotelService hotelService;


    private HotelController() {
        setUp();
    }

    public static HotelController getInstance() {
        if(instance == null){		//если объект еще не создан
            instance = new HotelController();	//создать новый объект
        }
        return instance;		// вернуть ранее созданный объект
    }

    private void createDao(){
        mainDao = new MainDao();
        guestDao = new GuestDao();
        roomDao = new RoomDao();
        serviceDao = new ServiceDao();
    }

    public void setUp() {
        createDao();
        mainDao.createHotelBase();
        mainDao.createTableGuests();
        mainDao.createLinkTableServices();
        mainDao.createTableHistoryGuests();

        mainDao.createTableRooms();
        mainDao.createLinkTableRooms();
        mainDao.createTableServices();
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
        roomService.addRoom(room);
    }
    public void deleteRoom(int indexRoom) {
        roomService.deleteRoom(roomService.viewRoom(indexRoom));
    }


    public void addService() {
        Random RANDOM = new Random();
        Service service = new Service(listNameServices[RANDOM.nextInt(listNameServices.length - 1)], RANDOM.nextInt(99) + 1);
        hotelService.addService(service);
    }
    public void deleteService(int indexService) {
        hotelService.deleteService(hotelService.viewService(indexService));
    }

    public void addGuest() {
        Random RANDOM = new Random();
        String nameGuest = listNameGuests[RANDOM.nextInt(listNameGuests.length-1)];
        Guest guest = new Guest(nameGuest);
        guestService.addGuest(guest);
    }
    public void deleteGuest(int indexGuest) {
        guestService.deleteGuest(guestService.viewGuest(indexGuest));
    }


    public void viewRooms() {
        roomService.listNumber();
        //ArrayList<Room> rooms = roomService.getRooms();
        //rooms.forEach(System.out::println);
    }

    public void changePriceRoom(int newPrice, int indexRoom) {
        Random RANDOM = new Random();
        List<Room> rooms = roomService.getRooms();
        roomService.changePriceRoom(newPrice, rooms.get(RANDOM.nextInt(rooms.size() - 1)));
    }

    public void changeStateRoom(StateRoom stateRoom, int indexRoom) {
        Random RANDOM = new Random();
        List<Room> rooms = roomService.getRooms();
        roomService.changeStateRoom(stateRoom, rooms.get(RANDOM.nextInt(rooms.size() - 1)));
    }

    public void viewService() {
        hotelService.listOrder();
    }

    public void changePriceService(int newPrice, int indexService) {
        Random RANDOM = new Random();
        List<Service> services = hotelService.getServices();
        hotelService.changePriceOrder(RANDOM.nextInt(services.size() - 1), newPrice);
    }

    public void checkInGuest() {
        List<Guest> guests = guestService.getGuests();
        for (int i = 0; i < guests.size(); i++) {
            Guest guest = guests.get(i);
            if (guest.getStateGuest()==StateGuest.NO_STATE) {
                LocalDate today = LocalDate.now();
                guest.setStateGuest(StateGuest.CHECK_IN);
                guest.setDateOfCheckIn(today);
                Random RANDOM = new Random();
                guest.setDateOfCheckOut(today.plusDays(RANDOM.nextInt(5)+1));
                roomService.checkIn(guest);
                guestService.updateGuest(guest);
                break;
            }

        }
    }

    public void checkOutGuest() {
        List<Guest> guests = guestService.getGuests();
        for (int i = 0; i < guests.size(); i++) {
            Guest guest = guests.get(i);
            if (guest.getStateGuest() == StateGuest.CHECK_IN) {
                guestService.leave(guest);
            }
        }
    }

    public void viewGuests() {
        guestService.listGuests();
    }
}
