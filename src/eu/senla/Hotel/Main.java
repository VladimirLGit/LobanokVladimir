// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com

package eu.senla.Hotel;

import eu.senla.Hotel.dao.GuestDao;
import eu.senla.Hotel.dao.RoomDao;
import eu.senla.Hotel.dao.ServiceDao;
import org.junit.Before;

public class Main {
    private GuestDao guestDao;
    private RoomDao roomDao;
    private ServiceDao serviceDao;

    private void createDao(){
        guestDao = new GuestDao();
        roomDao = new RoomDao();
        serviceDao = new ServiceDao();
    }


    @Before
    public void createTable(){
        guestDao.createTableGuests();
        guestDao.createLinkTableServices();
        roomDao.createTableRooms();
        roomDao.createLinkTableRooms();
        serviceDao.createTableServices();
    }

    public static void main(String[] args) throws InterruptedException {

    }
}