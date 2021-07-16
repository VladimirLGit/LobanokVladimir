package eu.senla.hotel.ui.menu;

import eu.senla.hotel.dao.Connector;

import java.util.Scanner;

public class MenuController {
    private static MenuController instance;
    private Builder builder;
    private Navigator navigator;
    private MenuController() {
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
        Connector c = new Connector();
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
