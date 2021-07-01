package eu.senla.hotel.dao;

import eu.senla.hotel.api.dao.IRoomDao;
import eu.senla.hotel.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDao implements IRoomDao {
    private final Connector connector;

    public RoomDao() {
        connector = new Connector();
    }

    public List<Integer> checkInGuests(Room room) {
        final String QUERY = "select IDGuest from linkTable where IDRoom = ?";
        List<Integer> guests = new ArrayList<>();
        try (Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setInt(1, room.getIdRoom());
            ResultSet rs = preparedStatement.executeQuery(QUERY);
            while (rs.next()) {
                guests.add(rs.getInt(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return guests;
    }

    public Room checkGuest(int idGuest) {
        int idRoom = 0;
        Room room = null;
        //final String QUERY = "select idRoom from linkTable where idGuest=?;";
        final String QUERY = "select idRoom FROM linkTable WHERE idGuest = " + '"' + idGuest + '"';
        try (Connection con = connector.getConnection();
             Statement query = con.createStatement()) {
            ResultSet rs = query.executeQuery(QUERY);
            while (rs.next()) {
                idRoom = rs.getInt(1);
                final String QUERY2 = "select * FROM rooms WHERE idRoom = " + '"' + idRoom + '"';
                Statement query2 = con.createStatement();
                ResultSet rs2 = query2.executeQuery(QUERY2);
                while (rs2.next()) {
                    idRoom = rs2.getInt("idRoom");
                    int number = rs2.getInt("Number");
                    int numberOfGuest = rs2.getInt("NumberOfGuests");
                    int price = rs2.getInt("Price");
                    double rating = rs2.getDouble("rating");
                    StateRoom stateRoom = StateRoom.values()[rs2.getInt("StateRoom")];
                    TypeRoom typeRoom = TypeRoom.values()[rs2.getInt("TypeRoom")];
                    room = new Room(number, price, numberOfGuest, typeRoom);
                    room.setIdRoom(idRoom);
                    room.setStateRoom(stateRoom);
                    room.setRating(rating);
                }
                rs2.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return room;
    }

    public void addLinkGuestWithRoom(Guest guest, Room room) {
        final String QUERY = "INSERT INTO linkTable (idRoom, idGuest) VALUES (?,?)";
        try (Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setInt(1, room.getIdRoom());
            preparedStatement.setInt(2, guest.getIdGuest());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void addRoom(Room room) {
        final String QUERY = "INSERT INTO rooms (number, numberOfGuests, price, rating, stateRoom, typeRoom) VALUES (?,?,?,?,?,?);";
        //return idRoom into ?;
        try (Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setInt(1, room.getNumber());
            preparedStatement.setInt(2, room.getNumberOfGuests());
            preparedStatement.setInt(3, room.getPrice());
            preparedStatement.setDouble(4, room.getRating());
            preparedStatement.setInt(5, room.getStateRoom().ordinal());
            preparedStatement.setInt(6, room.getTypeRoom().ordinal());
            preparedStatement.execute();

            // get the generated key for the id
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int id;
            if (rs.next()) {
                id = rs.getInt(1);
                room.setIdRoom(id);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteRoom(Room room) {
        final String QUERY = "Delete FROM rooms WHERE idRoom = " + '"' + room.getIdRoom() + '"';
        try (Connection con = connector.getConnection();
             Statement query = con.createStatement()) {
            int execute = query.executeUpdate(QUERY);
            if (execute > 0) {
                System.out.println("Delete room as " + room.getNumber());
            } else {
                System.out.println("The room does not exist");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateRoom(Room room) {
        final String QUERY = "UPDATE rooms SET`number`=?, `numberOfGuests`=?, `price`=?, `rating`=?, `stateRoom`=?, `typeRoom` =? WHERE `idRoom` =?;";
        try (Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setInt(1, room.getNumber());
            preparedStatement.setInt(2, room.getNumberOfGuests());
            preparedStatement.setInt(3, room.getPrice());
            preparedStatement.setDouble(4, room.getRating());
            preparedStatement.setInt(5, room.getStateRoom().ordinal());
            preparedStatement.setInt(6, room.getTypeRoom().ordinal());
            preparedStatement.setInt(7, room.getIdRoom());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    public List<Room> allRooms() {
        final String QUERY = "select * from rooms";
        List<Room> rooms = new ArrayList<>();
        try (Connection con = connector.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(QUERY)) {

            while (rs.next()) {
                int idRoom = rs.getInt("IDRoom");
                int number = rs.getInt("Number");
                int numberOfGuest = rs.getInt("NumberOfGuests");
                int price = rs.getInt("Price");
                double rating = rs.getDouble("rating");
                StateRoom stateRoom = StateRoom.values()[rs.getInt("StateRoom")];
                TypeRoom typeRoom = TypeRoom.values()[rs.getInt("TypeRoom")];
                Room room = new Room(number, price, numberOfGuest, typeRoom);
                room.setIdRoom(idRoom);
                room.setStateRoom(stateRoom);
                room.setRating(rating);
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }
}
