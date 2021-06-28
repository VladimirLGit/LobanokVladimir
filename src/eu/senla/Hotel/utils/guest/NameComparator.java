package eu.senla.Hotel.utils.guest;

import eu.senla.Hotel.model.Guest;

import java.util.Comparator;

public class NameComparator  implements Comparator,Cloneable  {
    @Override
    public int compare(Object o1, Object o2) {
        return ((Guest) o1).getName().compareTo(((Guest) o2).getName());
    }
}
