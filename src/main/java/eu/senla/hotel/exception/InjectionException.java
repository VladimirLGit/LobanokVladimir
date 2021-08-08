package eu.senla.hotel.exception;

public class InjectionException extends RuntimeException {
    public InjectionException(String massage, Throwable cause) {
        super(massage, cause);
    }

    public InjectionException(String massage) {
        super(massage);
    }
}
