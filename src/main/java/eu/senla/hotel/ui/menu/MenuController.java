package eu.senla.hotel.ui.menu;

import eu.senla.hotel.Main;
import eu.senla.hotel.dao.ds.DataSourceFactory;
import eu.senla.hotel.dependency2.injector.Injector;
import eu.senla.hotel.ui.HotelController;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Component
public class MenuController {
    private static MenuController instance;
    //private final Injector injector = new Injector();
    private Builder builder;
    private Navigator navigator;

    private MenuController() {
        //builder = Builder.getInstance();
        //try {
            //this.injector.initFramework(Main.class);
            //StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
            //Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
            //this.injector.getService(HotelController.class).setUp(DataSourceFactory.getDataSource());
            //builder = this.injector.getService(Builder.class);


        //} catch (Exception ex) {
        //    ex.printStackTrace();
        //}
        //builder.buildMenu();
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
    @Autowired
    @Qualifier("builder")
    public void setBuilder(Builder builder) {
        this.builder = builder;
    }
}
