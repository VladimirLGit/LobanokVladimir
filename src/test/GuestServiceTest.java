import eu.senla.hotel.api.dao.IGuestDao;
import eu.senla.hotel.api.dao.IRoomDao;
import eu.senla.hotel.api.dao.IServiceDao;
import eu.senla.hotel.api.sevice.IGuestService;
import eu.senla.hotel.api.sevice.IRoomService;
import eu.senla.hotel.api.sevice.IServiceService;
import eu.senla.hotel.dao.GuestDao;
import eu.senla.hotel.dao.MainDao;
import eu.senla.hotel.dao.RoomDao;
import eu.senla.hotel.dao.ServiceDao;
import eu.senla.hotel.dao.ds.DataSourceFactory;
import eu.senla.hotel.exception.NoFreeRoomInTheHotel;
import eu.senla.hotel.exception.NotExistObject;
import eu.senla.hotel.model.*;
import eu.senla.hotel.service.GuestService;
import eu.senla.hotel.service.HotelService;
import eu.senla.hotel.service.RoomService;
import eu.senla.hotel.utils.guest.CheckOutComparator;
import eu.senla.hotel.utils.guest.NameComparator;
import eu.senla.hotel.utils.room.NumberComparator;
import eu.senla.hotel.utils.room.NumberOfGuestsComparator;
import eu.senla.hotel.utils.room.PriceComparator;
import eu.senla.hotel.utils.room.RatingComparator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class GuestServiceTest {
    private final String[] listNameGuests = new String[]{
            "Tom",
            "Jack",
            "Rose",
            "Alice",
            "Oscar",
            "Leo",
            "John"};
    private MainDao mainDao;
    private IGuestDao guestDao;
    private IRoomDao roomDao;
    private IServiceDao serviceDao;
    private IGuestService guestService;
    private IRoomService roomService;
    private IServiceService hotelService;

    private void createDao(DataSource ds) {
        mainDao = new MainDao(ds);
        guestDao = new GuestDao(ds);
        roomDao = new RoomDao(ds);
        serviceDao = new ServiceDao(ds);
    }

    private void createRooms(){
        Random RANDOM = new Random();
        for (int i = 0; i < 10; i++) {
            Room room = new Room(i+1,
                              (i+1)*10,
                    RANDOM.nextInt(5)+1,
                    TypeRoom.values()[RANDOM.nextInt(TypeRoom.values().length)]);
            roomService.addRoom(room);//setRooms(roomDao.allRooms());
        }
    }

    private void createServices(){
        String[] listNameServices = new String[]{"?????????? ?????? ?? ??????????",
                                                "???????????? ????????????",
                                                "????????????",
                                                "?????????? ??????????",
                                                "???????????? ????????????",
                                                "?????????????? ??????????????????",
                                                "?????????????????????? ??????"};
        Random RANDOM = new Random();
        for (String listNameService : listNameServices) {
            Service service = new Service(listNameService, RANDOM.nextInt(99) + 1);
            hotelService.addService(service);
        }
    }

    @Before
    public void setUp() {
        DataSource ds = null;
        try {
            ds = DataSourceFactory.getDataSource();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        createDao(ds);
        mainDao.createHotelBase();
        mainDao.createTableGuests();
        mainDao.createLinkTableServices();
        mainDao.createTableHistoryGuests();

        mainDao.createTableRooms();
        mainDao.createLinkTableRooms();
        mainDao.createTableServices();
        guestService = new GuestService(ds);
        roomService = new RoomService(ds);
        createRooms();
        hotelService = new HotelService(ds);
        createServices();
    }

    @After
    public void tearDown() {
        mainDao.deleteTableGuests();
        mainDao.deleteTableHistoryGuests();
        mainDao.deleteLinkTableServices();
        mainDao.deleteTableRooms();
        mainDao.deleteLinkTableRooms();
        mainDao.deleteTableServices();
    }

    @Test
    public void addOrderGuest() {
        Guest guest = new Guest("Tom");
        guestService.enter(guest);
        roomService.checkIn(guest);
        try {
            guestDao.updateGuest(guest);
        } catch (NotExistObject notExistObject) {
            notExistObject.printStackTrace();
        }
        Service service = new Service("?????????? ?????? ?? ??????????",15);
        guestService.orderService(guest, service);
        Assert.assertFalse(guest.getOrderedServices().isEmpty());
        Service service2 = new Service("????????????",45);
        guestService.orderService(guest, service2);
    }

    @Test
    public void viewListGuestsAndSort(){
        List<Guest> guests;
        addGuest();
        addGuest();
        addGuest();
        addGuest();
        addGuest();
        guests = guestDao.allGuests();
        System.out.println("???????????? ????????????");
        for (Guest guest : guests) {
            System.out.println(guest);
        }
        System.out.println("???????????? ???????????? ?????????????????????????????? ???? ?????????? ??????????");
        guests.sort(new NameComparator());
        for (Guest guest : guests) {
            System.out.println(guest);
        }
        System.out.println("???????????? ???????????? ?????????????????????????????? ???? ???????? ????????????");
        guests.sort(new CheckOutComparator());
        for (Guest guest : guests) {
            System.out.println(guest);
        }

    }

    @Test
    public void viewListFreeRoomAndSort(){
        List<Room> rooms;
        addGuest();
        addGuest();
        addGuest();
        addGuest();
        addGuest();
        try {
            rooms = roomService.listFreeRooms();
            System.out.println("???????????? ?????????????????? ??????????????");
            for (Room room : rooms) {
                System.out.println(room);
            }
            System.out.println("???????????? ?????????????? ?????????????????????????????? ???? ?????????????????????? ????????????");
            rooms.sort(new NumberOfGuestsComparator());
            for (Room room : rooms) {
                System.out.println(room);
            }
            System.out.println("???????????? ?????????????? ?????????????????????????????? ???? ????????????????");
            rooms.sort(new RatingComparator());
            for (Room room : rooms) {
                System.out.println(room);
            }

            System.out.println("???????????? ?????????????? ?????????????????????????????? ???? ??????????????????");
            rooms.sort(new PriceComparator());
            for (Room room : rooms) {
                System.out.println(room);
            }
        } catch (NoFreeRoomInTheHotel noFreeRoomInTheHotel) {
            noFreeRoomInTheHotel.printStackTrace();
        }

    }

    @Test
    public void viewListLastGuest(){
        addGuest();
        //addGuest();
        addGuest();
        List<Guest> guests;
        Random RANDOM = new Random();
        guests = guestDao.allGuests();
        Guest guestOut = guests.get(RANDOM.nextInt(guests.size()));
        Room room = roomDao.checkGuest(guestOut.getId());
        guestOut.setIdRoom(room.getId());

        roomService.checkOut(guestOut);
        try {
            serviceDao.deleteOrderGuest(guestOut);
        } catch (NotExistObject notExistObject) {
            notExistObject.printStackTrace();
        }
        guestService.leave(guestOut);
        guests = guestService.last3Guests();
        for (Guest guest : guests) {
            System.out.println(guest);
        }
    }

    @Test
    public void checkOutAndDeleteOrderGuests() {
        List<Guest> guests;
        guests = guestDao.allGuests();
        Random RANDOM = new Random();

        for (Guest guest : guests) {
            Room room = roomDao.checkGuest(guest.getId());
            guest.setIdRoom(room.getId());
            List<Service> services = hotelService.getServices();

            for (int i = 0; i < 3; i++) {
                Service service = services.get(RANDOM.nextInt(services.size()));
                guestService.orderService(guest, service);
                hotelService.order(guest, service);
            }
            roomService.checkOut(guest);
            System.out.println(guest);

            List<Integer> orderedServices = guest.getOrderedServices();
            int priceServices = 0;
            for (Integer idService : orderedServices) {
                Service service = serviceDao.readService(idService);
                priceServices += service.getPrice();
            }
            try {
                serviceDao.deleteOrderGuest(guest);
            } catch (NotExistObject notExistObject) {
                notExistObject.printStackTrace();
            }
            orderedServices = guest.getOrderedServices();
            Assert.assertTrue(orderedServices.isEmpty());
            guestService.leave(guest);
            System.out.println("?? ???????????? = " + priceServices + "$ ???? ?????? ????????????");

        }
    }

    @Test
    public void willBeFreeInTheFuture(){
        List<Room> rooms;
        addGuest();
        addGuest();
        addGuest();
        addGuest();
        addGuest();
        List<Guest> guests = guestDao.allGuests();
        System.out.println("???????????? ????????????");
        for (Guest guest : guests) {
            System.out.println(guest);
        }
        LocalDate date = LocalDate.now();
        System.out.println(date.plusDays(2));
        rooms = roomService.listFreeRoomsForDate(date.plusDays(2));
        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    @Test
    public void sortRooms(){
        checkOutAndDeleteOrderGuests();
        List<Guest> guests;
        List<Room> rooms;
        addGuest();
        addGuest();
        addGuest();
        rooms = roomService.getRooms();
        System.out.println("???????????? ???????? ????????????");
        for (Room room : rooms) {
            System.out.println(room);
        }
        System.out.println("???????????????????? ???? ????????????????");
        rooms.sort(new RatingComparator());
        for (Room room : rooms) {
            System.out.println(room);
        }
        System.out.println("???????????????????? ???? ?????????????????? ????????????????????");
        rooms.sort(new PriceComparator());
        for (Room room : rooms) {
            System.out.println(room);
        }
        System.out.println("???????????????????? ???? ????????????");
        rooms.sort(new NumberComparator());
        for (Room room : rooms) {
            System.out.println(room);
        }
        System.out.println("???????????????????? ???? ?????????????????????? ????????????");
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
        Assert.assertEquals(guest.getState(), StateGuest.CHECK_IN);
        roomService.checkIn(guest);
        Assert.assertNotEquals(guest.getDateOfCheckIn(),null);
        Assert.assertNotEquals(guest.getDateOfCheckOut(),null);
        Assert.assertNotEquals(java.util.Optional.ofNullable(guest.getIdRoom()),-1);

        try {
            guestDao.updateGuest(guest);
        } catch (NotExistObject notExistObject) {


        }
    }

    @Test
    public void deleteGuest() {
        List<Guest> guests;
        Random RANDOM = new Random();
        Guest guest = new Guest("Tom");
        guestService.enter(guest);
        roomService.checkIn(guest);
        try {
            guestDao.updateGuest(guest);
        } catch (NotExistObject notExistObject) {
            notExistObject.printStackTrace();
        }
        guests = guestDao.allGuests();
        if (guests.size()>0) {
            Guest guestGet = guests.get(RANDOM.nextInt(guests.size()));
            guestService.leave(guestGet);
            Guest guest2 = guestDao.readGuest(guestGet.getId());
            Assert.assertNull(guest2);

        }


    }

}