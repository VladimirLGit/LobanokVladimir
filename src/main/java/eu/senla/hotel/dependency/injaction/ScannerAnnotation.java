package eu.senla.hotel.dependency.injaction;

import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ScannerAnnotation {
    private String CLASS_EXTENSION = ".class";
    private final Set<Class<?>> foundClasses;
    public ScannerAnnotation() {
        this.foundClasses = new HashSet<>();
    }
    public Set<Class<?>> findClasses(Class<?> startClass) {
        String directory = getDirectory(startClass);
        File file = new File(directory);
        if (!file.isDirectory()) {
            throw new RuntimeException("Invalid directory: " + directory);
        }
        try {
            for (File innerFile : Objects.requireNonNull(file.listFiles())) {
                scanDirectory(innerFile);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return foundClasses;
    }

    private void scanDirectory(File file) throws ClassNotFoundException {
        String packageName = "";
        if (file.isDirectory()) {
            packageName += file.getName() + ".";
            for (File innerFile: Objects.requireNonNull(file.listFiles())) {
                scanDirectory(innerFile);
            }
        }
        if (!file.getName().endsWith(CLASS_EXTENSION)) {
            return;
        }
        String className = packageName + file.getName().replace(CLASS_EXTENSION, "");
        foundClasses.add(Class.forName(className));
    }

    private String getDirectory(Class<?> clazz) {
        return clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
    }
}
