package eu.senla.hotel.dao;

import eu.senla.hotel.dao.Connector;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MainDao {
    private Connector connector;

    public MainDao() {
        connector = new Connector();
    }

    public boolean createHotelBase() {
        final String QUERY = "CREATE DATABASE IF NOT EXISTS hotelBase;";
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement();) {
            boolean execute = stmt.execute(QUERY);
            return execute;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean createTableGuests(){
        final String QUERY ="CREATE TABLE IF NOT EXISTS guests (\n" +
                " idGuest int(10) NOT NULL AUTO_INCREMENT,\n" +
                " name varchar(20) NOT NULL,\n" +
                " DateOfCheckIn date NOT NULL,\n" +
                " DateOfCheckOut date NOT NULL,\n" +
                " StateGuest int(10) NOT NULL,\n" +
                " PRIMARY KEY (idGuest)\n" +
                ");";
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement();) {
            boolean execute = stmt.execute(QUERY);
            return execute;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean deleteTableGuests(){
        final String QUERY ="DROP TABLE guests;";
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement();) {
            boolean execute = stmt.execute(QUERY);
            return execute;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean createLinkTableServices(){
        final String QUERY ="CREATE TABLE IF NOT EXISTS linkService (\n" +
                " idService int(10) NOT NULL,\n" +
                " idGuest int(10) NOT NULL\n" +
                ");";
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement();) {
            boolean execute = stmt.execute(QUERY);
            return execute;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean deleteLinkTableServices(){
        final String QUERY ="DROP TABLE linkService;";
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement();) {
            boolean execute = stmt.execute(QUERY);
            return execute;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean createTableHistoryGuests(){
        final String QUERY ="CREATE TABLE IF NOT EXISTS historyGuests (\n" +
                " idGuest int(10) NOT NULL AUTO_INCREMENT,\n" +
                " name varchar(20) NOT NULL,\n" +
                " DateOfCheckIn date NOT NULL,\n" +
                " DateOfCheckOut date NOT NULL,\n" +
                " StateGuest int(10) NOT NULL,\n" +
                " PRIMARY KEY (idGuest)\n" +
                ");";
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement();) {
            boolean execute = stmt.execute(QUERY);
            return execute;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean deleteTableHistoryGuests(){
        final String QUERY ="DROP TABLE historyGuests;";
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement();) {
            boolean execute = stmt.execute(QUERY);
            return execute;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean createTableRooms(){
        final String QUERY ="CREATE TABLE IF NOT EXISTS rooms (\n" +
                " idRoom int(10) NOT NULL AUTO_INCREMENT,\n" +
                " number int(10) NOT NULL,\n" +
                " numberOfGuests int(10) NOT NULL,\n" +
                " price int(10) NOT NULL,\n" +
                " stateRoom int(10) NOT NULL,\n" +
                " typeRoom int(10) NOT NULL,\n" +
                " PRIMARY KEY (idRoom)\n" +
                ");";
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement();) {
            boolean execute = stmt.execute(QUERY);
            return execute;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean deleteTableRooms(){
        final String QUERY ="DROP TABLE rooms;";
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement();) {
            boolean execute = stmt.execute(QUERY);
            return execute;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean createLinkTableRooms(){
        final String QUERY ="CREATE TABLE IF NOT EXISTS linkTable (\n" +
                " idRoom int(10) NOT NULL,\n" +
                " idGuest int(10) NOT NULL\n" +
                ");";
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement();) {
            boolean execute = stmt.execute(QUERY);
            return execute;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean deleteLinkTableRooms(){
        final String QUERY ="DROP TABLE linkTable;";
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement();) {
            boolean execute = stmt.execute(QUERY);
            return execute;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }


    public boolean createTableServices(){
        final String QUERY ="CREATE TABLE IF NOT EXISTS services (\n" +
                " idService int(10) NOT NULL AUTO_INCREMENT,\n" +
                " name varchar(20) NOT NULL,\n" +
                " price int(10) NOT NULL,\n" +
                " PRIMARY KEY (idService)\n" +
                ");";
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement();) {
            boolean execute = stmt.execute(QUERY);
            return execute;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean deleteTableServices(){
        final String QUERY ="DROP TABLE services;";
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement();) {
            boolean execute = stmt.execute(QUERY);
            return execute;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

}
