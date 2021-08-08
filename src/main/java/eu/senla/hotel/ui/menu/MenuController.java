package eu.senla.hotel.ui.menu;

import eu.senla.hotel.dependency2.injector.Injector;
import eu.senla.hotel.ui.HotelController;
import eu.senla.hotel.ui.actions.AbstractAction;

import java.io.IOException;
import java.util.Scanner;

public class MenuController {
    private static MenuController instance;
    private Builder builder;
    private Navigator navigator;
    private MenuController() {
        Injector injector;
        injector = new Injector();
        try {
            injector.initFramework(MenuController.class);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        builder = Builder.getInstance();
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
