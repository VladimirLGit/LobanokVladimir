package eu.senla.Hotel.dao;

import eu.senla.Hotel.api.dao.IGuestDao;
import eu.senla.Hotel.exception.NotExistObject;
import eu.senla.Hotel.model.Guest;
import eu.senla.Hotel.model.Service;
import eu.senla.Hotel.model.StateGuest;
import eu.senla.Hotel.utils.DateUtils;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;


public class GuestDao implements IGuestDao {
    private final Connector connector;

    public GuestDao() {
        connector = new Connector();
    }
    public void createHotelBase() {
        final String QUERY = "CREATE DATABASE IF NOT EXISTS hotelBase;";
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createTableGuests(){
        final String QUERY ="CREATE TABLE IF NOT EXISTS guests (\n" +
                " idGuest int(10) NOT NULL AUTO_INCREMENT,\n" +
                " name varchar(20) NOT NULL,\n" +
                " DateOfCheckIn date NOT NULL,\n" +
                " DateOfCheckOut date NOT NULL,\n" +
                " StateGuest int(10) NOT NULL,\n" +
                " PRIMARY KEY (idGuest)\n" +
                ");";
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteTableGuests(){
        final String QUERY ="DROP TABLE guests;";
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createLinkTableServices(){
        final String QUERY ="CREATE TABLE IF NOT EXISTS linkService (\n" +
                " idService int(10) NOT NULL,\n" +
                " idGuest int(10) NOT NULL\n" +
                ");";
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteLinkTableServices(){
        final String QUERY ="DROP TABLE linkService;";
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createTableHistoryGuests(){
        final String QUERY ="CREATE TABLE IF NOT EXISTS historyGuests (\n" +
                " idGuest int(10) NOT NULL AUTO_INCREMENT,\n" +
                " name varchar(20) NOT NULL,\n" +
                " DateOfCheckIn date NOT NULL,\n" +
                " DateOfCheckOut date NOT NULL,\n" +
                " StateGuest int(10) NOT NULL,\n" +
                " PRIMARY KEY (idGuest)\n" +
                ");";
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteTableHistoryGuests(){
        final String QUERY ="DROP TABLE historyGuests;";
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    private LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    public void addGuestIntoHistory(Guest guest) {
        final String QUERY = "INSERT INTO historyGuests (Name, DateOfCheckIn, DateOfCheckOut, StateGuest) VALUES (?,?,?,?)";
        //return idRoom into ?;
        try (Connection con = connector.getConnection()){
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setString(1, guest.getName());
            preparedStatement.setDate(2, java.sql.Date.valueOf(guest.getDateOfCheckIn()));
            preparedStatement.setDate(3, java.sql.Date.valueOf(guest.getDateOfCheckOut()));
            preparedStatement.setInt(4, guest.getState().ordinal());
            preparedStatement.execute();

            // get the generated key for the id
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int id;
            if (rs.next()) {
                id = rs.getInt(1);
                guest.setId(id);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
//INSERT IGNORE INTO `guests` (`IDGuest`, `Name`, `DateOfCheckIn`, `DateOfCheckOut`, `StateGuest`) VALUES (NULL, 'Tom', '2021-06-01', '2021-06-05', '1');
    @Override
    public void addGuest(Guest guest) {
        final String QUERY = "INSERT IGNORE INTO guests (Name, DateOfCheckIn, DateOfCheckOut, StateGuest) VALUES (?,?,?,?);";
        //return idRoom into ?;
        try (Connection con = connector.getConnection()){
             PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setString(1, guest.getName());
            preparedStatement.setDate(2, null);
            preparedStatement.setDate(3, null);
            preparedStatement.setInt(4, guest.getState().ordinal());
            preparedStatement.execute();

            // get the generated key for the id
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int id;
            if (rs.next()) {
                id = rs.getInt(1);
                guest.setId(id);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteGuest(Guest guest) throws NotExistObject {
        final String QUERY = "Delete FROM guests WHERE idGuest = " + '"' + guest.getId() + '"';
        try (Connection con = connector.getConnection();
             Statement query =  con.createStatement()) {
            int execute = query.executeUpdate(QUERY);
            if (execute>0) {
                System.out.println("Delete guest as " + guest.getName());
                addGuestIntoHistory(guest);
            } else {
                throw new NotExistObject("The guest does not exist");
                //System.out.println("The guest does not exist");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
//UPDATE `guests` SET `Name`='Tom',`DateOfCheckIn`='2021-06-01',`DateOfCheckOut`='2021-06-06',`StateGuest`=1 WHERE `IDGuest`=16
    @Override
    public void updateGuest(Guest guest) {
        final String QUERY = "UPDATE guests SET `name`=?, `DateOfCheckIn`=?, `DateOfCheckOut`=?, `StateGuest`=? WHERE `idGuest`=?;";
        try (Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setString(1, guest.getName());
            preparedStatement.setDate(2, java.sql.Date.valueOf(guest.getDateOfCheckIn()));
            preparedStatement.setDate(3, java.sql.Date.valueOf(guest.getDateOfCheckOut()));
            preparedStatement.setInt(4, guest.getState().ordinal());
            preparedStatement.setInt(5, guest.getId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Guest readGuest(int idGuest) {
        Guest guest = null;
        final String QUERY = "select * FROM guests WHERE idGuest = " + '"' + idGuest + '"';
        try (Connection con = connector.getConnection();
             Statement query = con.createStatement()) {
            ResultSet rs = query.executeQuery(QUERY);
            while(rs.next()) {
                int idReadGuest = rs.getInt("IDGuest");
                String nameGuest = rs.getString("Name");
                LocalDate localDateIn = DateUtils.asLocalDate(rs.getDate("DateOfCheckIn"));
                LocalDate localDateOut = DateUtils.asLocalDate(rs.getDate("DateOfCheckOut"));
                StateGuest stateRoom = StateGuest.values()[rs.getInt("StateGuest")];
                guest = new Guest(nameGuest);
                guest.setId(idReadGuest);
                guest.setState(stateRoom);
                guest.setDateOfCheckIn(localDateIn);
                guest.setDateOfCheckOut(localDateOut);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return guest;
    }
    @Override
    public ArrayList<Guest> allGuests() {
        final String QUERY = "select * from guests";
        ArrayList<Guest> guests = new ArrayList<>();
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY)) {
            while(rs.next()) {
                int idGuest = rs.getInt("IDGuest");
                String nameGuest = rs.getString("Name");
                LocalDate localDateIn = DateUtils.asLocalDate(rs.getDate("DateOfCheckIn"));
                LocalDate localDateOut = DateUtils.asLocalDate(rs.getDate("DateOfCheckOut"));
                StateGuest stateRoom = StateGuest.values()[rs.getInt("StateGuest")];
                Guest guest = new Guest(nameGuest);
                guest.setId(idGuest);
                guest.setState(stateRoom);
                guest.setDateOfCheckIn(localDateIn);
                guest.setDateOfCheckOut(localDateOut);
                guests.add(guest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return guests;
    }

    public ArrayList<Guest> last3Guests() {
        final String QUERY = "select * from historyGuests";
        ArrayList<Guest> guests = new ArrayList<>();
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY)) {

            while(rs.next()){
                int idGuest = rs.getInt("IDGuest");
                String nameGuest = rs.getString("Name");
                LocalDate localDateIn = convertToLocalDateViaSqlDate(rs.getDate("DateOfCheckIn"));
                LocalDate localDateOut = convertToLocalDateViaSqlDate(rs.getDate("DateOfCheckOut"));
                StateGuest stateRoom = StateGuest.values()[rs.getInt("StateGuest")];
                Guest guest = new Guest(nameGuest);
                guest.setId(idGuest);
                guest.setState(stateRoom);
                guest.setDateOfCheckIn(localDateIn);
                guest.setDateOfCheckOut(localDateOut);
                guests.add(guest);
                if (guests.size()==3) {
                    break;
                }
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
        return guests;
    }

    public Service readService(int idService) {
        final String QUERY = "select * from services WHERE IDService = ?;";
        Service service = null;
        try (Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setInt(1, idService);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("IDService");
                String nameService = rs.getString("Name");
                int price = rs.getInt("Price");
                service = new Service(nameService, price);
                service.setId(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return service;
    }

    public ArrayList<Service> allServicesGuest(Guest guest) {
        ArrayList<Service> services = null;
        final String QUERY = "select * from linkService WHERE idGuest = ?;";
        try (Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setInt(1, guest.getId());
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                int idService = rs.getInt("IDService");
                Service service = readService(idService);
                if (service!=null) {
                    services.add(service);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }
}
