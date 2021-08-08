package eu.senla.hotel.dependency.injaction;

import eu.senla.hotel.exception.InjectionException;

import java.lang.reflect.InvocationTargetException;

public class ObjectFactory {

    public <T> T createBean(Class<T> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new InjectionException("Bean creation failed", e);
        }
    }
}
