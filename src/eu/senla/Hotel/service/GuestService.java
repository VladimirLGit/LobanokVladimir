package eu.senla.hotel.service;

import eu.senla.hotel.api.sevice.IGuestService;
import eu.senla.hotel.dao.GuestDao;
import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.Service;
import eu.senla.hotel.model.StateGuest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GuestService implements IGuestService {
    private final GuestDao guestDao;

    private final List<Guest> guests;

    public GuestService() {
        guestDao = new GuestDao();
        guests = guestDao.allGuests();
    }

    @Override
    public void addGuest(Guest guest) {
        guests.add(guest);
        guestDao.addGuest(guest);
    }

    @Override
    public void deleteGuest(Guest guest) {
        int index = guests.indexOf(guest);
        if (index!=-1){
            guest.setStateGuest(StateGuest.CHECK_OUT);
            guestDao.deleteGuest(guest);
            guests.remove(index);
        }
    }

    public void updateGuest(Guest guest) {
        guestDao.updateGuest(guest);
    }

    @Override
    public void enter(Guest guest) {
        Random RANDOM = new Random();
        LocalDate today = LocalDate.now();
        guest.setStateGuest(StateGuest.CHECK_IN);
        guest.setDateOfCheckIn(today);
        guest.setDateOfCheckOut(today.plusDays(RANDOM.nextInt(5)+1));
        guests.add(guest);
        guestDao.addGuest(guest);
    }

    @Override
    public void leave(Guest guest) {
        int index = guests.indexOf(guest);
        if (index!=-1){
            guest.setStateGuest(StateGuest.CHECK_OUT);
            guestDao.deleteGuest(guest);
            guests.remove(index);
        }
    }

    public List<Guest> getGuests() {
        return guests;
    }

    @Override
    public void orderService(Guest guest, Service service) {
        guest.addOrderedService(service);
        //guestDao.addOrderGuest(guest, service);
    }

    @Override
    public List<Guest> last3Guests() {
        List<Guest> last3Guest = new ArrayList<>();
        for (int i = guests.size()-1; i >= 0; i--) {
           last3Guest.add(guests.get(i));
           if (last3Guest.size()==3) break;
        }

        if (last3Guest.size()<3) {
            //если не хватает или нет гостей в списке, тогда последние из истории
            List<Guest> last3GuestHistory = guestDao.last3Guests();
            if (last3GuestHistory.size()>0)
                while ((last3Guest.size()!=3) && (last3GuestHistory.size()>0)){
                    last3Guest.add(last3GuestHistory.remove(0));
                }

        }
        return last3Guest;
    }

    @Override
    public int amountGuests() {
        return guests.size();
    }

    @Override
    public Guest viewGuest(int indexGuest) {
        if (indexGuest<guests.size()){
            System.out.println(guests.get(indexGuest));
            return guests.get(indexGuest);
        }
        else
            System.out.println("Такого гостя нет");
        return null;
    }

    public void listGuests() {
        for (Guest guest : guests) {
            System.out.println(guest);
        }
    }
}
