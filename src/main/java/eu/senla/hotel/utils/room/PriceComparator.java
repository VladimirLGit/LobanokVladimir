package eu.senla.hotel.utils.room;

import eu.senla.hotel.model.Room;

import java.util.Comparator;

public class PriceComparator implements Comparator,Cloneable  {
    @Override
    public int compare(Object o1, Object o2) {
        return Integer.compare(((Room) o1).getPrice(), ((Room) o2).getPrice());
    }
}
