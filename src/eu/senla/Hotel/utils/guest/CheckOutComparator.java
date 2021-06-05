package eu.senla.Hotel.utils.guest;

import eu.senla.Hotel.model.Guest;

import java.time.LocalDate;
import java.util.Comparator;

public class CheckOutComparator implements Comparator,Cloneable  {
    @Override
    public int compare(Object o1, Object o2) {
        return ((Guest) o1).getDateOfCheckOut().compareTo(((Guest) o2).getDateOfCheckOut());
    }
}
