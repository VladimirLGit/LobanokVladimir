// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com

package eu.senla.hotel;

import eu.senla.hotel.springioc.DataSourceFactory;
import eu.senla.hotel.springioc.SpringConfig;
import eu.senla.hotel.ui.HotelController;
import eu.senla.hotel.ui.actions.ViewRooms;
import eu.senla.hotel.ui.menu.MenuController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        MenuController menuController = context.getBean(MenuController.class);
        menuController.run();

        //ApplicationContext act = SpringApplication.run(SpringConfig.class, args);
        //HotelController obj = (HotelController) context.getBean(HotelController.class);
//        try {
//            obj.setUp((DataSource) context.getBean(DataSourceFactory.class).getDataSource());
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        obj.viewRooms();
        //ViewRooms  obj = (ViewRooms) context.getBean(ViewRooms.class);
        //obj.execute();
        //MenuController menuController = context.getBean(MenuController.class);
        //menuController.run();
        //MenuController.getInstance().run();
    }
}