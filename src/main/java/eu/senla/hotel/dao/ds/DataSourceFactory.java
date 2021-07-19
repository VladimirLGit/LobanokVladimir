package eu.senla.hotel.dao.ds;

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

        Class mClassObject = ConfigConnector.class;
        Field fieldDbUrl = null;
        Field fieldUserName = null;
        Field fieldPassword = null;
        try {
            fieldDbUrl = mClassObject.getDeclaredField("dbUrl");
            fieldUserName = mClassObject.getDeclaredField("userName");
            fieldPassword = mClassObject.getDeclaredField("password");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        ConfigApplication annotation;
        annotation = fieldDbUrl.getAnnotation(ConfigApplication.class);
        DB_URL = annotation.value();
        annotation = fieldUserName.getAnnotation(ConfigApplication.class);
        USERNAME = annotation.value();
        annotation = fieldPassword.getAnnotation(ConfigApplication.class);
        PASSWORD = annotation.value();
        dataSource.setDatabaseName("hotelBase");
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setUser(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setServerTimezone("UTC");
        return dataSource;
    }
}
