package eu.senla.Hotel.dao;

import eu.senla.Hotel.api.dao.IGuestDao;
import eu.senla.Hotel.model.Guest;
import eu.senla.Hotel.model.Service;
import eu.senla.Hotel.model.StateGuest;
import eu.senla.Hotel.utils.DateUtils;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;


public class GuestDao implements IGuestDao {
    private Connector connector;

    public GuestDao() {
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


    private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    public void addOrderGuest(Guest guest, Service service) {
        final String QUERY = "INSERT INTO linkService (idGuest, idService) VALUES (?,?)";
        //return idRoom into ?;
        try (Connection con = connector.getConnection();){
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setInt(1, guest.getIdGuest());
            preparedStatement.setInt(2, service.getIdService());
            boolean execute = preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteOrderGuest(Guest guest) {
        final String QUERY = "Delete FROM linkService WHERE idGuest = " + '"' + Integer.toString(guest.getIdGuest()) + '"';
        try (Connection con = connector.getConnection();
             Statement query =  con.createStatement();) {
            int execute = query.executeUpdate(QUERY);
            if (execute>0) {
                System.out.println("Delete orders as " + guest.getNameGuest());
            } else {
                System.out.println("The orders does not exist");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addGuestIntoHistory(Guest guest) {
        final String QUERY = "INSERT INTO historyGuests (Name, DateOfCheckIn, DateOfCheckOut, StateGuest) VALUES (?,?,?,?)";
        //return idRoom into ?;
        try (Connection con = connector.getConnection();){
            //default time zone
            ZoneId defaultZoneId = ZoneId.systemDefault();
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setString(1, guest.getNameGuest());
            //local date + atStartOfDay() + default time zone + toInstant() = Date
            //Date date = (Date) Date.from(guest.getDateOfCheckIn().atStartOfDay(defaultZoneId).toInstant());
            Date date = DateUtils.asDate(guest.getDateOfCheckIn());
            preparedStatement.setDate(2, java.sql.Date.valueOf(guest.getDateOfCheckIn()));
            //date = (Date) Date.from(guest.getDateOfCheckOut().atStartOfDay(defaultZoneId).toInstant());
            preparedStatement.setDate(3, java.sql.Date.valueOf(guest.getDateOfCheckOut()));
            preparedStatement.setInt(4, guest.getStateGuest().ordinal());
            boolean execute = preparedStatement.execute();

            // get the generated key for the id
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int id = -1;
            if (rs.next()) {
                id = rs.getInt(1);
                guest.setIdGuest(id);
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
        try (Connection con = connector.getConnection();){
            //default time zone
            //ZoneId defaultZoneId = ZoneId.systemDefault();
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setString(1, guest.getNameGuest());
            //local date + atStartOfDay() + default time zone + toInstant() = Date
            //Date date = (Date) Date.from(guest.getDateOfCheckIn().atStartOfDay(defaultZoneId).toInstant());
            //preparedStatement.setDate(2, date);
            //date = (Date) Date.from(guest.getDateOfCheckOut().atStartOfDay(defaultZoneId).toInstant());
            //preparedStatement.setDate(3, date);
            preparedStatement.setDate(2, new java.sql.Date(DateUtils.asDate(guest.getDateOfCheckIn()).getTime()));
            preparedStatement.setDate(3, new java.sql.Date(DateUtils.asDate(guest.getDateOfCheckOut()).getTime()));
            //preparedStatement.setDate(2, java.sql.Date.valueOf(guest.getDateOfCheckIn()));
            //preparedStatement.setDate(3, java.sql.Date.valueOf(guest.getDateOfCheckOut()));
            preparedStatement.setInt(4, guest.getStateGuest().ordinal());
            boolean execute = preparedStatement.execute();

            // get the generated key for the id
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int id = -1;
            if (rs.next()) {
                id = rs.getInt(1);
                guest.setIdGuest(id);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteGuest(Guest guest) {
        final String QUERY = "Delete FROM guests WHERE idGuest = " + '"' + Integer.toString(guest.getIdGuest()) + '"';
        try (Connection con = connector.getConnection();
             Statement query =  con.createStatement();) {
            int execute = query.executeUpdate(QUERY);
            if (execute>0) {
                System.out.println("Delete guest as " + guest.getNameGuest());
                addGuestIntoHistory(guest);
            } else {
                System.out.println("The guest does not exist");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
//UPDATE `guests` SET `Name`='Tom',`DateOfCheckIn`='2021-06-01',`DateOfCheckOut`='2021-06-06',`StateGuest`=1 WHERE `IDGuest`=16
    @Override
    public void updateGuest(Guest guest) {
        final String QUERY = "UPDATE guests SET `name`=?, `DateOfCheckIn`=?, `DateOfCheckOut`=?, `StateGuest`=? WHERE `idGuest`=?;";
        try (Connection con = connector.getConnection();) {
            //default time zone
            //ZoneId defaultZoneId = ZoneId.systemDefault();
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setString(1, guest.getNameGuest());
            //Date date = (Date) Date.from(guest.getDateOfCheckIn().atStartOfDay(defaultZoneId).toInstant());
            //preparedStatement.setDate(2, date);
            //date = (Date) Date.from(guest.getDateOfCheckOut().atStartOfDay(defaultZoneId).toInstant());
            //preparedStatement.setDate(3, date);
            preparedStatement.setDate(2, java.sql.Date.valueOf(guest.getDateOfCheckIn()));
            preparedStatement.setDate(3, java.sql.Date.valueOf(guest.getDateOfCheckOut()));
            preparedStatement.setInt(4, guest.getStateGuest().ordinal());
            preparedStatement.setInt(5, guest.getIdGuest());
            boolean execute = preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public ArrayList<Guest> allGuests() {
        final String QUERY = "select * from guests";
        ArrayList<Guest> guests = new ArrayList<>();
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY)) {
            //stmt.close();
            while(rs.next()){
                int idGuest = rs.getInt("IDGuest");
                String nameGuest = rs.getString("Name");
                LocalDate localDateIn = DateUtils.asLocalDate(rs.getDate("DateOfCheckIn"));
                LocalDate localDateOut = DateUtils.asLocalDate(rs.getDate("DateOfCheckOut"));
                StateGuest stateRoom = StateGuest.values()[rs.getInt("StateGuest")];
                Guest guest = new Guest(nameGuest);
                guest.setIdGuest(idGuest);
                guest.setStateGuest(stateRoom);
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
            stmt.close();
            while(rs.next()){
                int idGuest = rs.getInt("IDGuest");
                String nameGuest = rs.getString("Name");
                LocalDate localDateIn = convertToLocalDateViaSqlDate(rs.getDate("DateOfCheckIn"));
                LocalDate localDateOut = convertToLocalDateViaSqlDate(rs.getDate("DateOfCheckOut"));
                StateGuest stateRoom = StateGuest.values()[rs.getInt("StateGuest")];
                Guest guest = new Guest(nameGuest);
                guest.setIdGuest(idGuest);
                guest.setStateGuest(stateRoom);
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
}
