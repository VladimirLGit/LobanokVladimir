package eu.senla.hotel.springioc;

import eu.senla.hotel.dao.GuestDao;
import eu.senla.hotel.dao.MainDao;
import eu.senla.hotel.dao.RoomDao;
import eu.senla.hotel.dao.ServiceDao;
import eu.senla.hotel.dao.collection.LGuestDao;
import eu.senla.hotel.ui.actions.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.sql.SQLException;

@Configuration
@PropertySource("classpath:datasource.properties")
public class SpringConfig {
    @Value("${mysql.DB}")
    String db;

    @Value("${mysql.PASSWORD}")
    String password;

    @Value("${mysql.USERNAME}")
    String username;

    @Value("${mysql.HOST}")
    String host;

    @Value("${mysql.PORT}")
    String port;

    @Bean
    public GuestDao guestDao() {
        DataSourceFactory dataSource = new DataSourceFactory();
        dataSource.setDb(db);
        dataSource.setHost(host);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        try {
            return new GuestDao(dataSource.getDataSource());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Bean
    public RoomDao roomDao() {
        DataSourceFactory dataSource = new DataSourceFactory();
        dataSource.setDb(db);
        dataSource.setHost(host);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        try {
            return new RoomDao(dataSource.getDataSource());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Bean
    public ServiceDao serviceDao() {
        DataSourceFactory dataSource = new DataSourceFactory();
        dataSource.setDb(db);
        dataSource.setHost(host);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        try {
            return new ServiceDao(dataSource.getDataSource());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Bean
    public MainDao mainDao() {
        DataSourceFactory dataSource = new DataSourceFactory();
        dataSource.setDb(db);
        dataSource.setHost(host);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        try {
            return new MainDao(dataSource.getDataSource());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Bean
    public AddGuest addGuest() {
        return new AddGuest();
    }

    @Bean
    public AddRoom addRoom() {
        return new AddRoom();
    }
    @Bean
    public AddService addService() {
        return new AddService();
    }
    @Bean
    public CallService callService() {
        return new CallService();
    }
    @Bean
    public ChangePriceRoom changePriceRoom() {
        return new ChangePriceRoom();
    }
    @Bean
    public ChangePriceService changePriceService() {
        return new ChangePriceService();
    }
    @Bean
    public ChangeStateRoom changeStateRoom() {
        return new ChangeStateRoom();
    }
    @Bean
    public CheckInGuest checkInGuest() {
        return new CheckInGuest();
    }
    @Bean
    public CheckOutGuest checkOutGuest() {
        return new CheckOutGuest();
    }
    @Bean
    public DeleteGuest deleteGuest() {
        return new DeleteGuest();
    }
    @Bean
    public DeleteRoom deleteRoom() {
        return new DeleteRoom();
    }
    @Bean
    public DeleteService deleteService() {
        return new DeleteService();
    }
    @Bean
    public DeserializationObject deserializationObject() {
        return new DeserializationObject();
    }
    @Bean
    public SerializationsObjects serializationsObjects() {
        return new SerializationsObjects();
    }
    @Bean
    public ViewGuestsHotel viewGuestsHotel() {
        return new ViewGuestsHotel();
    }
    @Bean
    public ViewRooms viewRooms() {
        return new ViewRooms();
    }
    @Bean
    public ViewServices viewServices() {
        return new ViewServices();
    }

}
