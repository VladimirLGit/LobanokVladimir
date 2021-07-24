package eu.senla.mysql.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MainDao {
    private final DataSource connector;

    public MainDao(DataSource ds) {
        connector = ds;
    }

    public void createMarketBase() {
        final String QUERY = "CREATE DATABASE IF NOT EXISTS marketBase;";
        try (Connection con = connector.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void createTableProducts() {
        final String QUERY = "CREATE TABLE `products` (\n" +
                " `maker` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,\n" +
                " `model` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,\n" +
                " `type` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
        try (Connection con = connector.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createTablePCs() {
        final String QUERY = "CREATE TABLE `pcs` (\n" +
                " `code` int(11) NOT NULL AUTO_INCREMENT,\n" +
                " `model` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,\n" +
                " `speed` int(11) NOT NULL,\n" +
                " `ram` double NOT NULL,\n" +
                " `hd` double NOT NULL,\n" +
                " `cd` int(11) NOT NULL,\n" +
                " `price` int(11) NOT NULL,\n" +
                " PRIMARY KEY (`code`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
        try (Connection con = connector.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createTableLaptops() {
        final String QUERY = "CREATE TABLE `laptops` (\n" +
                " `code` int(11) NOT NULL AUTO_INCREMENT,\n" +
                " `model` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,\n" +
                " `speed` int(11) NOT NULL,\n" +
                " `ram` double NOT NULL,\n" +
                " `hd` double NOT NULL,\n" +
                " `price` int(11) NOT NULL,\n" +
                " `screen` tinyint(4) NOT NULL,\n" +
                " PRIMARY KEY (`code`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
        try (Connection con = connector.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createTablePrinters() {
        final String QUERY = "CREATE TABLE `printers` (\n" +
                " `code` int(11) NOT NULL AUTO_INCREMENT,\n" +
                " `model` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,\n" +
                " `color` tinyint(4) NOT NULL,\n" +
                " `type` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,\n" +
                " `price` int(11) NOT NULL,\n" +
                " PRIMARY KEY (`code`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
        try (Connection con = connector.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



}
