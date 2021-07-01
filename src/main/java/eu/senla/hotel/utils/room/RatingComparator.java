package main.java.eu.senla.hotel.utils.room;

import main.java.eu.senla.hotel.model.Room;

import java.util.Comparator;

public class RatingComparator implements Comparator,Cloneable  {
    @Override
    public int compare(Object o1, Object o2) {
        return Double.compare(((Room) o1).getRating(), ((Room) o2).getRating());
    }
}
