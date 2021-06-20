package eu.senla.Hotel.dao;

import eu.senla.Hotel.api.dao.IRoomDao;
import eu.senla.Hotel.exception.NotExistObject;
import eu.senla.Hotel.model.*;

import java.sql.*;
import java.util.ArrayList;

public class RoomDao implements IRoomDao {
    private final Connector connector;

    public RoomDao() {
        connector = new Connector();
    }

    public void createTableRooms(){
        final String QUERY ="CREATE TABLE IF NOT EXISTS rooms (\n" +
                " idRoom int(10) NOT NULL AUTO_INCREMENT,\n" +
                " number int(10) NOT NULL,\n" +
                " numberOfGuests int(10) NOT NULL,\n" +
                " price int(10) NOT NULL,\n" +
                " rating double NOT NULL,\n" +
                " stateRoom int(10) NOT NULL,\n" +
                " typeRoom int(10) NOT NULL,\n" +
                " PRIMARY KEY (idRoom)\n" +
                ");";
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteTableRooms(){
        final String QUERY ="DROP TABLE rooms;";
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createLinkTableRooms(){
        final String QUERY ="CREATE TABLE IF NOT EXISTS linkTable (\n" +
                " idRoom int(10) NOT NULL,\n" +
                " idGuest int(10) NOT NULL\n" +
                ");";
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteLinkTableRooms(){
        final String QUERY ="DROP TABLE linkTable;";
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ArrayList<Integer> checkInGuests(Room room){
        final String QUERY = "select IDGuest from linkTable where IDRoom = ?";
        ArrayList<Integer> guests = new ArrayList<>();
        try(Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setInt(1, room.getIdRoom());
            ResultSet rs = preparedStatement.executeQuery(QUERY);
            while (rs.next()){
                guests.add(rs.getInt(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return guests;
    }

    public Room checkGuest(int idGuest){
        int idRoom = 0;
        Room room = null;
        //final String QUERY = "select idRoom from linkTable where idGuest=?;";
        final String QUERY = "select idRoom FROM linkTable WHERE idGuest = " + '"' + idGuest + '"';
        try(Connection con = connector.getConnection();
            Statement query = con.createStatement()) {
            ResultSet rs = query.executeQuery(QUERY);
            while (rs.next()){
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
        try (Connection con = connector.getConnection()){
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
        try (Connection con = connector.getConnection()){
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
    public void deleteRoom(Room room) throws NotExistObject{
        final String QUERY = "Delete FROM rooms WHERE idRoom = " + '"' + room.getIdRoom() + '"';
        try (Connection con = connector.getConnection();
             Statement query =  con.createStatement()) {
            int execute = query.executeUpdate(QUERY);
            if (execute>0) {
                System.out.println("Delete room as " + room.getNumber());
            } else {
                throw new NotExistObject("The room does not exist");
                //System.out.println("The room does not exist");
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
    public ArrayList<Room> allRooms() {
        final String QUERY = "select * from rooms";
        ArrayList<Room> rooms = new ArrayList<>();
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY)) {

            while(rs.next()){
                int idRoom = rs.getInt("IDRoom");
                int number = rs.getInt("Number");
                int numberOfGuest = rs.getInt("NumberOfGuests");
                int price = rs.getInt("Price");
                double rating = rs.getDouble("rating");
                StateRoom stateRoom = StateRoom.values()[rs.getInt("StateRoom")];
                TypeRoom typeRoom = TypeRoom.values()[rs.getInt("TypeRoom")];
                Room room = new Room(number, price,numberOfGuest, typeRoom);
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
