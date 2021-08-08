package eu.senla.hotel.dependency2.utils;

import eu.senla.hotel.dependency2.annotation.Autowired;
import eu.senla.hotel.dependency2.annotation.Qualifier;
import eu.senla.hotel.dependency2.injector.Injector;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;


public class InjectionUtil {

	private InjectionUtil() {
		super();
	}

	/**
	 * Perform injection recursively, for each service inside the Client class
	 */
	public static void autowire(Injector injector, Class<?> classz, Object classInstance)
			throws InstantiationException, IllegalAccessException {
		Set<Field> fields = findFields(classz);
		for (Field field : fields) {
			String qualifier = field.isAnnotationPresent(Qualifier.class)
					? field.getAnnotation(Qualifier.class).value()
					: null;
			Object fieldInstance = injector.getBeanInstance(field.getType(), field.getName(), qualifier);
			field.set(classInstance, fieldInstance);
			autowire(injector, fieldInstance.getClass(), fieldInstance);
		}
	}

	/**
	 * Get all the fields having Autowired annotation used while declaration
	 */
	private static Set<Field> findFields(Class<?> classz) {
		Set<Field> set = new HashSet<>();
		while (classz != null) {
			for (Field field : classz.getDeclaredFields()) {
				if (field.isAnnotationPresent(Autowired.class)) {
					field.setAccessible(true);
					set.add(field);
				}
			}
			classz = classz.getSuperclass();
		}
		return set;

	}
}
