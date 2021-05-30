package eu.senla.Hotel.service;

import eu.senla.Hotel.api.sevice.IRoomService;
import eu.senla.Hotel.model.Guest;
import eu.senla.Hotel.model.Room;
import eu.senla.Hotel.model.StateRoom;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class RoomService implements IRoomService {
    private ArrayList<Room> rooms;

    @Override
    public void checkIn(Guest guest) {
        LocalDate today = LocalDate.now();
        Random RANDOM = new Random();
        ArrayList<Room> freeRooms;
        freeRooms = listFreeRooms();
        Room room = freeRooms.get(RANDOM.nextInt(freeRooms.size()));
        guest.setRoom(room); //поселили гостя в комнату без критериев
        guest.setDateOfCheckIn(today);
    }


    @Override
    public void checkOut(Guest guest) {
        LocalDate today = LocalDate.now();
        Room room = guest.getRoom();
        guest.setRoom(null);
        guest.setDateOfCheckOut(today);
        //сумма к уплате за проживание и оказанные услуги
        int priceRoom = room.getPrice();
        int amountOfDaysOfStay = (int) DAYS.between(guest.getDateOfCheckIn(), guest.getDateOfCheckOut());
        System.out.println("К оплате = " + amountOfDaysOfStay*priceRoom + "$");
    }

    @Override
    public void listNumber() {
        for (int i = 0; i < rooms.size(); i++) {
            System.out.println(rooms.get(i));
        }
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
        if (room.getStateRoom() == StateRoom.CHECKED){
            guests = room.getGuests();
            for (int i = 0; i < guests.size(); i++) {
                if (guests.get(i).getDateOfCheckOut().isAfter(dateCheckOut)){
                    dateCheckOut = guests.get(i).getDateOfCheckOut();
                }
            }
            if (dateCheckOut.isBefore(date)) {
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
    public void viewRoom(int indexRoom) {
        if (indexRoom<rooms.size()){
            System.out.println(rooms.get(indexRoom));
        }
        else
            System.out.println("Номер не существует");
    }
}
