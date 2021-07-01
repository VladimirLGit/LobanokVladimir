package main.java.eu.senla.hotel.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
    private final String driver = "com.mysql.jdbc.Driver";
    private String USERNAME = "root";
    private String PASSWORD = "root";
    private String DB_URL = "jdbc:mysql://127.0.0.1:3306/hotelBase";

    public Connector() {
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/java/resources/config.properties");
            properties.load(fileInputStream);
            DB_URL = properties.getProperty("db.host");
            USERNAME = properties.getProperty("db.login");
            PASSWORD = properties.getProperty("db.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
