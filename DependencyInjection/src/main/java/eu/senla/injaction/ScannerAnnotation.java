package main.java.eu.senla.injaction;

import java.util.HashSet;
import java.util.Set;

public class ScannerAnnotation {
    private final Set<Class<?>> foundClasses;
    public ScannerAnnotation() {
        this.foundClasses = new HashSet<>();
    }
    public Set<Class<?>> findClasses(Class<?> startClass) {
        return null;
    }
}
