package eu.senla.Hotel.service;

import eu.senla.Hotel.api.sevice.IRoomService;
import eu.senla.Hotel.dao.RoomDao;
import eu.senla.Hotel.model.Guest;
import eu.senla.Hotel.model.Room;
import eu.senla.Hotel.model.Service;
import eu.senla.Hotel.model.StateRoom;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class RoomService implements IRoomService {
    private final RoomDao roomDao;

    private final ArrayList<Room> rooms;

    public RoomService() {
        roomDao = new RoomDao();
        rooms = roomDao.allRooms();
    }

    @Override
    public void addRoom(Room room) {
        roomDao.addRoom(room);
        rooms.add(room);
    }

    @Override
    public void deleteRoom(Room room) {
        if (room!=null)
          roomDao.deleteRoom(room);
    }

    @Override
    public void checkIn(Guest guest) {
        Random RANDOM = new Random();
        ArrayList<Room> freeRooms;
        freeRooms = listFreeRooms();
        Room room = freeRooms.get(RANDOM.nextInt(freeRooms.size()));
        room.setStateRoom(StateRoom.CHECKED);
        room.addGuest(guest);
        roomDao.updateRoom(room);
        roomDao.addLinkGuestWithRoom(guest, room);
        guest.setRoom(room); //поселили гостя в комнату без критериев
    }


    @Override
    public void checkOut(Guest guest) {
        Random RANDOM = new Random();
        LocalDate today = LocalDate.now();
        Room room = guest.getRoom();
        int amountOfDaysOfStay = 0;
        int priceRoom = 0;
        int indexRoom = rooms.indexOf(room);
        if (indexRoom>=0){
            room = rooms.get(indexRoom);
            room.setRating(RANDOM.nextInt(5));
            room.setStateRoom(StateRoom.FREE);
            room.deleteGuest(guest);
            roomDao.updateRoom(room);
            guest.setRoom(null);
            guest.setDateOfCheckOut(today);
            //сумма к уплате за проживание и оказанные услуги
            priceRoom = room.getPrice();
            amountOfDaysOfStay = (int) DAYS.between(guest.getDateOfCheckIn(), guest.getDateOfCheckOut()) + 1;
        }


        System.out.println("К оплате = " + amountOfDaysOfStay*priceRoom + "$ за проживание");
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    @Override
    public void listNumber() {
        for (Room room : rooms) {
            System.out.println(room);
        }
    }
    public void changePriceRoom(int newPrice, Room room) {
        System.out.println("До " + room);
        room.setPrice(newPrice);
        roomDao.updateRoom(room);
        System.out.println("После измениения цены");
        System.out.println(room);
    }

    void addStateRoom(Room room, StateRoom stateRoom, ArrayList<Room> list)
    {
        if (room.getStateRoom() == stateRoom){
            list.add(room);
        }
    }

    void addStateRoom(Room room, StateRoom stateRoom, LocalDate date, ArrayList<Room> list)
    {
        ArrayList<Guest> guests;
        LocalDate dateCheckOut = date;
        if (room.getStateRoom() == stateRoom){
            list.add(room);
        }
        else
        if (room.getStateRoom() == StateRoom.CHECKED){
            guests = room.getGuests();
            for (Guest guest : guests) {
                LocalDate localDate = guest.getDateOfCheckOut();
                if (localDate.isAfter(dateCheckOut)) {
                    dateCheckOut = guest.getDateOfCheckOut();
                }
            }
            if (dateCheckOut.equals(date) || dateCheckOut.isBefore(date)) {
                list.add(room);
            }
        }
    }

    @Override
    public ArrayList<Room> listFreeRooms() {
        ArrayList<Room> freeRooms = new ArrayList<>();
        rooms.forEach(room -> addStateRoom(room, StateRoom.FREE, freeRooms));
        return freeRooms;
    }

    @Override
    public ArrayList<Room> listCheckedRooms() {
        ArrayList<Room> checkedRooms = new ArrayList<>();
        rooms.forEach(room -> addStateRoom(room, StateRoom.CHECKED, checkedRooms));
        return checkedRooms;
    }

    @Override
    public int amountFreeRooms() {
        return listFreeRooms().size();
    }

    @Override
    public ArrayList<Room> listFreeRoomsForDate(LocalDate date) {
        ArrayList<Room> freeRooms = new ArrayList<>();
        rooms.forEach(room -> addStateRoom(room, StateRoom.FREE, date, freeRooms));
        return freeRooms;
    }

    @Override
    public Room viewRoom(int indexRoom) {
        if (indexRoom<rooms.size()){
            System.out.println(rooms.get(indexRoom));
            return rooms.get(indexRoom);
        }
        else
            System.out.println("Номер не существует");
        return null;
    }

    public void changeStateRoom(StateRoom stateRoom, Room room) {
        System.out.println("До " + room);
        room.setStateRoom(stateRoom);
        roomDao.updateRoom(room);
        System.out.println("После измениения статуса");
        System.out.println(room);
    }
}
