package eu.senla.hotel.dao;

import eu.senla.hotel.annotations.ConfigApplication;
import eu.senla.hotel.utils.ConfigConnector;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
    ConfigConnector configConnector;
    private final String driver = "com.mysql.cj.jdbc.Driver";
    private String USERNAME = "root";
    private String PASSWORD = "root";
    private String DB_URL = "jdbc:mysql://127.0.0.1:3306/hotelBase";



    public Connector() {
        /*configConnector = new ConfigConnector();
        DB_URL = configConnector.getDbUrl();
        USERNAME = configConnector.getUserName();
        PASSWORD = configConnector.getPassword();*/
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
    }

    public Connection getConnection() {
        Properties props = new Properties();
        Connection con = null;
        try {
            // load the Driver Class
            props.setProperty("password"         , PASSWORD);
            props.setProperty("user"             , USERNAME);
            props.setProperty("url"              , DB_URL  );
            props.setProperty("useUnicode"       , "true"  );
            props.setProperty("characterEncoding", "utf8"  );
            Class.forName(driver);

            // create the connection now
            con = DriverManager.getConnection(props.getProperty("url"),
                    props.getProperty("user"),
                    props.getProperty("password"));
        } catch (ClassNotFoundException | SQLException e) {
             e.printStackTrace();
        }
        return con;
    }
}
