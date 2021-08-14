package eu.senla.hotel;

import eu.senla.hotel.dependency2.MyApplication;
import eu.senla.hotel.ui.menu.MenuController;

public class UserApplication {
    public static void main(String[] args) {
        MyApplication.run(UserApplication.class, args);
        MenuController.getInstance().run();
    }
}
