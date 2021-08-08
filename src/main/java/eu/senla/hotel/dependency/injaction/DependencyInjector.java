package eu.senla.hotel.dependency.injaction;

import eu.senla.hotel.exception.InjectionException;

public class DependencyInjector {
    public static void run(Class<?> startClass, ApplicationContext context) {
        try {
            ScannerAnnotation scannerAnnotation = new ScannerAnnotation();
            ObjectFactory factory = new ObjectFactory();
            context.setFactory(factory);
            context.createContext(scannerAnnotation.findClasses(startClass));
        } catch (IllegalAccessException e) {
            throw new InjectionException("Message", e);
        }
    }
}
