package eu.senla.hotel.dao;

import eu.senla.hotel.api.dao.IGuestDao;
import eu.senla.hotel.exception.NotExistObject;
import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.StateGuest;
import eu.senla.hotel.utils.DateUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class GuestDao implements IGuestDao {
    private final DataSource connector;
    public GuestDao(DataSource ds) {
        connector = ds;
    }
    private LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }
    public void addGuestIntoHistory(Guest guest) {
        final String QUERY = "INSERT INTO historyGuests (Name, DateOfCheckIn, DateOfCheckOut, StateGuest) VALUES (?,?,?,?)";
        //return idRoom into ?;
        try (Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setString(1, guest.getName());
            preparedStatement.setDate(2, java.sql.Date.valueOf(guest.getDateOfCheckIn()));
            preparedStatement.setDate(3, java.sql.Date.valueOf(guest.getDateOfCheckOut()));
            preparedStatement.setInt(4, guest.getState().ordinal());
            preparedStatement.execute();
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
            PreparedStatement preparedStatement = con.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS);
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
             Statement query = con.createStatement()) {
            int execute = query.executeUpdate(QUERY);
            if (execute > 0) {
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
    public void updateGuest(Guest guest) throws NotExistObject {
        final String QUERY = "UPDATE guests SET `name`=?, `DateOfCheckIn`=?, `DateOfCheckOut`=?, `StateGuest`=? WHERE `idGuest`=?;";
        try (Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setString(1, guest.getName());
            preparedStatement.setDate(2, java.sql.Date.valueOf(guest.getDateOfCheckIn()));
            preparedStatement.setDate(3, java.sql.Date.valueOf(guest.getDateOfCheckOut()));
            preparedStatement.setInt(4, guest.getState().ordinal());
            preparedStatement.setInt(5, guest.getId());
            if (preparedStatement.executeUpdate() == 0)
                throw new NotExistObject("The guest does not exist");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Guest readGuest(int id) {
        Guest guest = null;
        final String QUERY = "select * FROM guests WHERE idGuest = " + '"' + id + '"';
        try (Connection con = connector.getConnection();
             Statement query = con.createStatement()) {
            ResultSet rs = query.executeQuery(QUERY);
            while (rs.next()) {
                String name = rs.getString("Name");
                LocalDate localDateIn = DateUtils.asLocalDate(rs.getDate("DateOfCheckIn"));
                LocalDate localDateOut = DateUtils.asLocalDate(rs.getDate("DateOfCheckOut"));
                StateGuest state = StateGuest.values()[rs.getInt("StateGuest")];
                guest = new Guest(name);
                guest.setId(id);
                guest.setState(state);
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
                int id = rs.getInt("IDGuest");
                String name = rs.getString("Name");
                LocalDate localDateIn = DateUtils.asLocalDate(rs.getDate("DateOfCheckIn"));
                LocalDate localDateOut = DateUtils.asLocalDate(rs.getDate("DateOfCheckOut"));
                StateGuest state = StateGuest.values()[rs.getInt("StateGuest")];
                Guest guest = new Guest(name);
                guest.setId(id);
                guest.setState(state);
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
                int id = rs.getInt("IDGuest");
                String name = rs.getString("Name");
                LocalDate localDateIn = convertToLocalDateViaSqlDate(rs.getDate("DateOfCheckIn"));
                LocalDate localDateOut = convertToLocalDateViaSqlDate(rs.getDate("DateOfCheckOut"));
                StateGuest stateRoom = StateGuest.values()[rs.getInt("StateGuest")];
                Guest guest = new Guest(name);
                guest.setId(id);
                guest.setState(stateRoom);
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

    @Override
    public void setGuests(List<Guest> guests) {

    }

    public List<Integer> allServicesGuest(Guest guest) {
        List<Integer> services = null;
        final String QUERY = "select * from linkService WHERE idGuest = ?;";
        try (Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setInt(1, guest.getId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                services.add(rs.getInt("IDService"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }
}
