package eu.senla.Hotel.utils.room;

import eu.senla.Hotel.model.Room;

import java.util.Comparator;

public class NumberOfGuestsComparator implements Comparator,Cloneable  {
    @Override
    public int compare(Object o1, Object o2) {
        return Integer.compare(((Room) o1).getNumberOfGuests(), ((Room) o2).getNumberOfGuests());
    }
}
