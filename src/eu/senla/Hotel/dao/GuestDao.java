package eu.senla.Hotel.dao;

import eu.senla.Hotel.api.dao.IGuestDao;
import eu.senla.Hotel.model.Guest;
import eu.senla.Hotel.model.Service;
import eu.senla.Hotel.model.StateGuest;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;


public class GuestDao implements IGuestDao {
    private Connector connector;

    public GuestDao() {
        connector = new Connector();
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
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
            Date date = (Date) Date.from(guest.getDateOfCheckIn().atStartOfDay(defaultZoneId).toInstant());
            preparedStatement.setDate(2, date);
            date = (Date) Date.from(guest.getDateOfCheckOut().atStartOfDay(defaultZoneId).toInstant());
            preparedStatement.setDate(3, date);
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
    public void addGuest(Guest guest) {
        final String QUERY = "INSERT INTO guests (Name, DateOfCheckIn, DateOfCheckOut, StateGuest) VALUES (?,?,?,?)";
        //return idRoom into ?;
        try (Connection con = connector.getConnection();){
            //default time zone
            ZoneId defaultZoneId = ZoneId.systemDefault();
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setString(1, guest.getNameGuest());
            //local date + atStartOfDay() + default time zone + toInstant() = Date
            Date date = (Date) Date.from(guest.getDateOfCheckIn().atStartOfDay(defaultZoneId).toInstant());
            preparedStatement.setDate(2, date);
            date = (Date) Date.from(guest.getDateOfCheckOut().atStartOfDay(defaultZoneId).toInstant());
            preparedStatement.setDate(3, date);
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

    @Override
    public void updateGuest(Guest guest) {
        final String QUERY = "UPDATE guests name = ?, DateOfCheckIn = ?, DateOfCheckOut = ?, StateGuest = ? WHERE idGuest = ?";
        try (Connection con = connector.getConnection();) {
            //default time zone
            ZoneId defaultZoneId = ZoneId.systemDefault();
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setString(1, guest.getNameGuest());
            Date date = (Date) Date.from(guest.getDateOfCheckIn().atStartOfDay(defaultZoneId).toInstant());
            preparedStatement.setDate(2, date);
            date = (Date) Date.from(guest.getDateOfCheckOut().atStartOfDay(defaultZoneId).toInstant());
            preparedStatement.setDate(3, date);
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
