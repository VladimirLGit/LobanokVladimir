import eu.senla.Hotel.dao.GuestDao;
import eu.senla.Hotel.dao.RoomDao;
import eu.senla.Hotel.dao.ServiceDao;
import eu.senla.Hotel.model.*;
import eu.senla.Hotel.service.GuestService;
import eu.senla.Hotel.service.RoomService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

public class GuestServiceTest {
    private GuestDao guestDao;
    private RoomDao roomDao;
    private ServiceDao serviceDao;
    private GuestService guestService;
    private RoomService roomService;

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
        guests = guestDao.allGuests();
        for (Guest guest : guests) {
            Room room = roomDao.checkGuest(guest.getIdGuest());
            guest.setRoom(room);
            roomService.checkOut(guest);
            System.out.println(guest);

            ArrayList<Service> orderedServices = guest.getOrderedServices();
            int priceServices = 0;
            for (Service service : orderedServices) {
                priceServices =+ service.getPriceService();
            }
            guestDao.deleteOrderGuest(guest);
            orderedServices = guest.getOrderedServices();
            Assert.assertTrue(orderedServices.isEmpty());

            System.out.println("К оплате = " + priceServices + "$ за все услуги");
            
        }
    }

    @Test
    public void addGuest() {
        Guest guest = new Guest("Tom");
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