package eu.senla.hotel.ui.menu;

import eu.senla.hotel.Main;
import eu.senla.hotel.dao.ds.DataSourceFactory;
import eu.senla.hotel.dependency2.injector.Injector;
import eu.senla.hotel.ui.HotelController;

import java.util.Scanner;

public class MenuController {
    private static MenuController instance;
    private final Injector injector = new Injector();
    private Builder builder;
    private Navigator navigator;
    private MenuController() {
        builder = Builder.getInstance();
        try {
            this.injector.initFramework(Main.class);
            this.injector.getService(HotelController.class).setUp(DataSourceFactory.getDataSource());
            builder = this.injector.getService(Builder.class);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        builder.buildMenu();
        navigator = Navigator.getInstance();
    }

    public static MenuController getInstance() {
        if(instance == null){		//если объект еще не создан
            instance = new MenuController();	//создать новый объект
        }
        return instance;		// вернуть ранее созданный объект
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        navigator.setCurrentMenu(builder.getRootMenu());
        navigator.printMenu();
        int index = 1;
        while (true) {
            index = scanner.nextInt();
            if (navigator.navigate(index - 1)) {
                navigator.printMenu();
            }
            else
                break;

        }
    }
}
