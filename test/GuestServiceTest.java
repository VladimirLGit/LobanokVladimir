import eu.senla.Hotel.dao.GuestDao;
import eu.senla.Hotel.dao.RoomDao;
import eu.senla.Hotel.dao.ServiceDao;
import eu.senla.Hotel.model.*;
import eu.senla.Hotel.service.GuestService;
import eu.senla.Hotel.service.HotelService;
import eu.senla.Hotel.service.RoomService;
import eu.senla.Hotel.utils.guest.CheckOutComparator;
import eu.senla.Hotel.utils.guest.NameComparator;
import eu.senla.Hotel.utils.room.NumberComparator;
import eu.senla.Hotel.utils.room.NumberOfGuestsComparator;
import eu.senla.Hotel.utils.room.PriceComparator;
import eu.senla.Hotel.utils.room.RatingComparator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

public class GuestServiceTest {
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

    private void createRooms(){
        Random RANDOM = new Random();
        for (int i = 0; i < 10; i++) {
            Room room = new Room(i+1,
                              (i+1)*10,
                    RANDOM.nextInt(5)+1,
                    TypeRoom.values()[RANDOM.nextInt(TypeRoom.values().length)]);
            roomDao.addRoom(room);
        }
    }

    private void createServices(){
        String[] listNameServices = new String[]{"Заказ еды в номер",
                                                "Уборка номера",
                                                "Массаж",
                                                "Вызов такси",
                                                "Чистка одежды",
                                                "Покупка сувениров",
                                                "Тренажерный зал"};
        Random RANDOM = new Random();
        for (String listNameService : listNameServices) {
            Service service = new Service(listNameService, RANDOM.nextInt(99) + 1);
            hotelService.addService(service);
        }
    }


    @Before
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
        createRooms();
        roomService = new RoomService();
        hotelService = new HotelService();
        createServices();
    }


    @After
    public void tearDown() {
        guestDao.deleteTableGuests();
        guestDao.deleteTableHistoryGuests();
        guestDao.deleteLinkTableServices();
        roomDao.deleteTableRooms();
        roomDao.deleteLinkTableRooms();
        serviceDao.deleteTableServices();
    }

    @Test
    public void addOrderGuest() {
        Guest guest = new Guest("Tom");
        guestService.enter(guest);
        roomService.checkIn(guest);
        guestDao.updateGuest(guest);
        Service service = new Service("Заказ еды в номер",15);
        guestService.orderService(guest, service);
        Assert.assertFalse(guest.getOrderedServices().isEmpty());
        Service service2 = new Service("Массаж",45);
        guestService.orderService(guest, service2);
    }

    @Test
    public void checkOutAndDeleteOrderGuests() {
        ArrayList<Guest> guests;
        addGuest();
        addGuest();
        addGuest();
        addGuest();
        addGuest();
        guests = guestDao.allGuests();
        System.out.println("Список гостей");
        for (Guest guest : guests) {
            System.out.println(guest);
        }
        System.out.println("Список гостей отсортированный по имени гостя");
        guests.sort(new NameComparator());
        for (Guest guest : guests) {
            System.out.println(guest);
        }
        System.out.println("Список гостей отсортированный по дате выезда");
        guests.sort(new CheckOutComparator());
        for (Guest guest : guests) {
            System.out.println(guest);
        }

        Random RANDOM = new Random();

        for (Guest guest : guests) {
            Room room = roomDao.checkGuest(guest.getIdGuest());
            guest.setRoom(room);
            ArrayList<Service> services = hotelService.getServices();

            for (int i = 0; i < 3; i++) {
                Service service = services.get(RANDOM.nextInt(services.size()));
                guestService.orderService(guest, service);
                hotelService.order(guest, service);
            }
            roomService.checkOut(guest);
            System.out.println(guest);

            ArrayList<Service> orderedServices = guest.getOrderedServices();
            int priceServices = 0;
            for (Service service : orderedServices) {
                priceServices += service.getPriceService();
            }
            serviceDao.deleteOrderGuest(guest);
            orderedServices = guest.getOrderedServices();
            Assert.assertTrue(orderedServices.isEmpty());

            System.out.println("К оплате = " + priceServices + "$ за все услуги");
            
        }
    }


    @Test
    public void sortRooms(){
        checkOutAndDeleteOrderGuests();
        ArrayList<Guest> guests;
        ArrayList<Room> rooms;
        addGuest();
        addGuest();
        addGuest();
        rooms = roomService.getRooms();
        System.out.println("Список всех комнат");
        for (Room room : rooms) {
            System.out.println(room);
        }
        System.out.println("Сортировка по рейтингу");
        rooms.sort(new RatingComparator());
        for (Room room : rooms) {
            System.out.println(room);
        }
        System.out.println("Сортировка по стоимости проживания");
        rooms.sort(new PriceComparator());
        for (Room room : rooms) {
            System.out.println(room);
        }
        System.out.println("Сортировка по номеру");
        rooms.sort(new NumberComparator());
        for (Room room : rooms) {
            System.out.println(room);
        }
        System.out.println("Сортировка по вместимости гостей");
        rooms.sort(new NumberOfGuestsComparator());
        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    @Test
    public void addGuest() {
        Random RANDOM = new Random();
        String nameGuest = listNameGuests[RANDOM.nextInt(listNameGuests.length-1)];
        Guest guest = new Guest(nameGuest);
        guestService.enter(guest);
        Assert.assertEquals(guest.getStateGuest(), StateGuest.CHECK_IN);
        roomService.checkIn(guest);
        Assert.assertNotEquals(guest.getDateOfCheckIn(),null);
        Assert.assertNotEquals(guest.getDateOfCheckOut(),null);
        Assert.assertNotEquals(guest.getRoom(),null);

        guestDao.updateGuest(guest);
    }

    @Test
    public void deleteGuest() {
        ArrayList<Guest> guests;
        Random RANDOM = new Random();
        Guest guest = new Guest("Tom");
        guestService.enter(guest);
        roomService.checkIn(guest);
        guestDao.updateGuest(guest);
        guests = guestDao.allGuests();
        if (guests.size()>0) {
            Guest guestGet = guests.get(RANDOM.nextInt(guests.size()));
            guestService.leave(guestGet);
            Guest guest2 = guestDao.readGuest(guestGet.getIdGuest());
            Assert.assertNull(guest2);

        }


    }


    @Test
    public void last3Guests() {
    }
}