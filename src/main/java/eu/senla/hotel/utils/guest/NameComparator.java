package main.java.eu.senla.hotel.utils.guest;

import main.java.eu.senla.hotel.model.Guest;

import java.util.Comparator;

public class NameComparator  implements Comparator,Cloneable  {
    @Override
    public int compare(Object o1, Object o2) {
        return ((Guest) o1).getName().compareTo(((Guest) o2).getName());
    }
}
