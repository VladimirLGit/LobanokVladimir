package eu.senla.mysql.dao.ds;

import com.mysql.cj.jdbc.MysqlDataSource;
import eu.senla.hotel.annotations.ConfigApplication;
import eu.senla.hotel.utils.ConfigConnector;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.SQLException;

public class DataSourceFactory {

    public static DataSource getDataSource() throws SQLException {
        String USERNAME = "root";
        String PASSWORD = "root";
        String DB_URL = "jdbc:mysql://127.0.0.1:3306/hotelBase";
        MysqlDataSource dataSource = new MysqlDataSource();

        dataSource.setDatabaseName("hotelBase");
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setUser(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setServerTimezone("UTC");
        return dataSource;
    }
}
