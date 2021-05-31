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

        Service service = new Service("Заказ еды в номер",15);
        //guestService.orderService();
    }

    @Test
    public void deleteOrderGuest() {
    }

    @Test
    public void addGuest() {
        Guest guest = new Guest("Tom");
        roomService.checkIn(guest);
        Assert.assertNotEquals(guest.getDateOfCheckIn(),null);
        Assert.assertNotEquals(guest.getDateOfCheckOut(),null);
        Assert.assertNotEquals(guest.getRoom(),null);
        guestService.enter(guest);
        Assert.assertEquals(guest.getStateGuest(), StateGuest.CHECK_IN);
        guestDao.updateGuest(guest);
    }

    @Test
    public void deleteGuest() {
    }

    @Test
    public void updateGuest() {
    }

    @Test
    public void allGuests() {
    }

    @Test
    public void last3Guests() {
    }
}