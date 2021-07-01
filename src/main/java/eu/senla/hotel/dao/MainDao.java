package eu.senla.hotel.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MainDao {
    private final Connector connector;

    public MainDao() {
        connector = new Connector();
    }

    public void createHotelBase() {
        final String QUERY = "CREATE DATABASE IF NOT EXISTS hotelBase;";
        try (Connection con = connector.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createTableGuests() {
        final String QUERY = "CREATE TABLE IF NOT EXISTS guests (\n" +
                " idGuest int(10) NOT NULL AUTO_INCREMENT,\n" +
                " name varchar(20) NOT NULL,\n" +
                " DateOfCheckIn date NOT NULL,\n" +
                " DateOfCheckOut date NOT NULL,\n" +
                " StateGuest int(10) NOT NULL,\n" +
                " PRIMARY KEY (idGuest)\n" +
                ");";
        try (Connection con = connector.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteTableGuests() {
        final String QUERY = "DROP TABLE guests;";
        try (Connection con = connector.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createLinkTableServices() {
        final String QUERY = "CREATE TABLE IF NOT EXISTS linkService (\n" +
                " idService int(10) NOT NULL,\n" +
                " idGuest int(10) NOT NULL\n" +
                ");";
        try (Connection con = connector.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteLinkTableServices() {
        final String QUERY = "DROP TABLE linkService;";
        try (Connection con = connector.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createTableHistoryGuests() {
        final String QUERY = "CREATE TABLE IF NOT EXISTS historyGuests (\n" +
                " idGuest int(10) NOT NULL AUTO_INCREMENT,\n" +
                " name varchar(20) NOT NULL,\n" +
                " DateOfCheckIn date NOT NULL,\n" +
                " DateOfCheckOut date NOT NULL,\n" +
                " StateGuest int(10) NOT NULL,\n" +
                " PRIMARY KEY (idGuest)\n" +
                ");";
        try (Connection con = connector.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteTableHistoryGuests() {
        final String QUERY = "DROP TABLE historyGuests;";
        try (Connection con = connector.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createTableRooms() {
        final String QUERY = "CREATE TABLE IF NOT EXISTS rooms (\n" +
                " idRoom int(10) NOT NULL AUTO_INCREMENT,\n" +
                " number int(10) NOT NULL,\n" +
                " numberOfGuests int(10) NOT NULL,\n" +
                " price int(10) NOT NULL,\n" +
                " rating double NOT NULL,\n" +
                " stateRoom int(10) NOT NULL,\n" +
                " typeRoom int(10) NOT NULL,\n" +
                " PRIMARY KEY (idRoom)\n" +
                ");";
        try (Connection con = connector.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteTableRooms() {
        final String QUERY = "DROP TABLE rooms;";
        try (Connection con = connector.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createLinkTableRooms() {
        final String QUERY = "CREATE TABLE IF NOT EXISTS linkTable (\n" +
                " idRoom int(10) NOT NULL,\n" +
                " idGuest int(10) NOT NULL\n" +
                ");";
        try (Connection con = connector.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteLinkTableRooms() {
        final String QUERY = "DROP TABLE linkTable;";
        try (Connection con = connector.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createTableServices() {
        final String QUERY = "CREATE TABLE IF NOT EXISTS services (\n" +
                " idService int(10) NOT NULL AUTO_INCREMENT,\n" +
                " name varchar(20) NOT NULL,\n" +
                " price int(10) NOT NULL,\n" +
                " PRIMARY KEY (idService)\n" +
                ");";
        try (Connection con = connector.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteTableServices() {
        final String QUERY = "DROP TABLE services;";
        try (Connection con = connector.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
