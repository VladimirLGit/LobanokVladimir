package eu.senla.hotel.dao;

import eu.senla.hotel.api.dao.IGuestDao;
import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.Service;
import eu.senla.hotel.model.StateGuest;
import eu.senla.hotel.utils.DateUtils;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class GuestDao implements IGuestDao {
    private final Connector connector;

    public GuestDao() {
        connector = new Connector();
    }


    private LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    public void addGuestIntoHistory(Guest guest) {
        final String QUERY = "INSERT INTO historyGuests (Name, DateOfCheckIn, DateOfCheckOut, StateGuest) VALUES (?,?,?,?)";
        //return idRoom into ?;
        try (Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setString(1, guest.getNameGuest());
            preparedStatement.setDate(2, java.sql.Date.valueOf(guest.getDateOfCheckIn()));
            preparedStatement.setDate(3, java.sql.Date.valueOf(guest.getDateOfCheckOut()));
            preparedStatement.setInt(4, guest.getStateGuest().ordinal());
            preparedStatement.execute();

            // get the generated key for the id
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int id;
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
        try (Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setString(1, guest.getNameGuest());
            preparedStatement.setDate(2, new java.sql.Date(DateUtils.asDate(guest.getDateOfCheckIn()).getTime()));
            preparedStatement.setDate(3, new java.sql.Date(DateUtils.asDate(guest.getDateOfCheckOut()).getTime()));
            preparedStatement.setInt(4, guest.getStateGuest().ordinal());
            preparedStatement.execute();

            // get the generated key for the id
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int id;
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
        final String QUERY = "Delete FROM guests WHERE idGuest = " + '"' + guest.getIdGuest() + '"';
        try (Connection con = connector.getConnection();
             Statement query = con.createStatement()) {
            int execute = query.executeUpdate(QUERY);
            if (execute > 0) {
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
        try (Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setString(1, guest.getNameGuest());
            preparedStatement.setDate(2, java.sql.Date.valueOf(guest.getDateOfCheckIn()));
            preparedStatement.setDate(3, java.sql.Date.valueOf(guest.getDateOfCheckOut()));
            preparedStatement.setInt(4, guest.getStateGuest().ordinal());
            preparedStatement.setInt(5, guest.getIdGuest());
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
            while (rs.next()) {
                int idReadGuest = rs.getInt("IDGuest");
                String nameGuest = rs.getString("Name");
                LocalDate localDateIn = DateUtils.asLocalDate(rs.getDate("DateOfCheckIn"));
                LocalDate localDateOut = DateUtils.asLocalDate(rs.getDate("DateOfCheckOut"));
                StateGuest stateRoom = StateGuest.values()[rs.getInt("StateGuest")];
                guest = new Guest(nameGuest);
                guest.setIdGuest(idReadGuest);
                guest.setStateGuest(stateRoom);
                guest.setDateOfCheckIn(localDateIn);
                guest.setDateOfCheckOut(localDateOut);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return guest;
    }

    @Override
    public List<Guest> allGuests() {
        final String QUERY = "select * from guests";
        List<Guest> guests = new ArrayList<>();
        try (Connection con = connector.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(QUERY)) {
            while (rs.next()) {
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

    public List<Guest> last3Guests() {
        final String QUERY = "select * from historyGuests";
        List<Guest> guests = new ArrayList<>();
        try (Connection con = connector.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(QUERY)) {

            while (rs.next()) {
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
                if (guests.size() == 3) {
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
            while (rs.next()) {
                int id = rs.getInt("IDService");
                String nameService = rs.getString("Name");
                int price = rs.getInt("Price");
                service = new Service(nameService, price);
                service.setIdService(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return service;
    }

    public List<Service> allServicesGuest(Guest guest) {
        List<Service> services = null;
        final String QUERY = "select * from linkService WHERE idGuest = ?;";
        try (Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setInt(1, guest.getIdGuest());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int idService = rs.getInt("IDService");
                Service service = readService(idService);
                if (service != null) {
                    services.add(service);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }
}
