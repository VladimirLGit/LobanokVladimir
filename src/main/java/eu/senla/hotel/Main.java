// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com

package eu.senla.hotel;

import eu.senla.hotel.ui.menu.MenuController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ApplicationContext act = SpringApplication.run(MenuController.class, args);
        MenuController menuController = act.getBean(MenuController.class);
        menuController.run();
        //MenuController.getInstance().run();
    }
}