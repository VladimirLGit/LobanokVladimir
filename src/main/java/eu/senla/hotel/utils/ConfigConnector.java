package main.java.eu.senla.hotel.utils;


import main.java.eu.senla.hotel.annotations.ConfigApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigConnector {
    @ConfigApplication(name = "userName", value = "root")
    private String userName = "";
    @ConfigApplication(name = "password", value = "root")
    private String password = "";
    @ConfigApplication(name = "dbUrl", value = "jdbc:mysql://127.0.0.1:3306/hotelBase")
    private String dbUrl = "";

    public ConfigConnector() {
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/java/resources/config.properties");
            properties.load(fileInputStream);
            dbUrl = properties.getProperty("db.host");
            userName = properties.getProperty("db.login");
            password = properties.getProperty("db.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getDbUrl() {
        return dbUrl;
    }
}
