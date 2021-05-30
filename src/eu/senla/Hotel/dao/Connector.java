package eu.senla.Hotel.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
    private final String driver = "com.mysql.jdbc.Driver";
    private final String USERNAME = "root";
    private final String PASSWORD = "root";
    private final String DB_URL = "jdbc:mysql://127.0.0.1:3306/hotelBase";


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
