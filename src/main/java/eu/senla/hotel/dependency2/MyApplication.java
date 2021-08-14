package eu.senla.hotel.dependency2;

import eu.senla.hotel.UserApplication;
import eu.senla.hotel.dao.ds.DataSourceFactory;
import eu.senla.hotel.dependency2.injector.Injector;
import eu.senla.hotel.ui.HotelController;

import java.sql.SQLException;

public class MyApplication {
    private final Injector injector;

    public MyApplication() {
        this.injector = new Injector();
    }

    public void run(Class<?> mainClass) {
        System.out.println("Starting MyApplication...");
        this.startApplication(UserApplication.class);
        this.injector.getService(HotelController.class).getInstance();
        //this.injector.getService(HotelController.class).setUp(DataSourceFactory.getDataSource());
        System.out.println("\nStopping MyApplication...");
    }

    public void startApplication(Class<?> mainClass) {
        try {
            synchronized (MyApplication.class) {
                this.injector.initFramework(mainClass);
                System.out.println("\nMyApplication Started....");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void run(Class<?> mainClassz, String[] args) {
        new MyApplication().run(mainClassz);
    }

}
