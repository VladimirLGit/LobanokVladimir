package eu.senla.mysql.api;

import eu.senla.mysql.exception.NotExistObject;

import java.util.List;

public interface ItemDao<T,S> {
    void add(T item);
    void delete(T item) throws NotExistObject;
    void update(T item) throws NotExistObject;
    T get(S index);
    List<T> listItem();
}
