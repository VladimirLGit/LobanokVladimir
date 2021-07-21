package eu.senla.mysql.api;

import java.util.List;

public interface ItemDao<T> {
    void add(T item);
    void delete(T item);
    void update(T item);
    T get(int index);
    List<T> listItem();
}
