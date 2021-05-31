package eu.senla.Hotel.dao;

import eu.senla.Hotel.api.dao.IRoomDao;
import eu.senla.Hotel.model.Guest;
import eu.senla.Hotel.model.Room;
import eu.senla.Hotel.model.StateRoom;
import eu.senla.Hotel.model.TypeRoom;

import java.sql.*;
import java.util.ArrayList;

public class RoomDao implements IRoomDao {
    private Connector connector;

    public RoomDao() {
        connector = new Connector();
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

    public ArrayList<Integer> checkInGuests(Room room){
        final String QUERY = "select IDGuest from linkTable where IDRoom = ?";
        ArrayList<Integer> guests = new ArrayList<>();
        try(Connection con = connector.getConnection();) {
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
    @Override
    public void addRoom(Room room) {
        final String QUERY = "INSERT INTO rooms (number, numberOfGuests, price, stateRoom, typeRoom) VALUES (?,?,?,?,?);";
        //return idRoom into ?;
        try (Connection con = connector.getConnection();){
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setInt(1, room.getNumber());
            preparedStatement.setInt(2, room.getNumberOfGuests());
            preparedStatement.setInt(3, room.getPrice());
            preparedStatement.setInt(4, room.getStateRoom().ordinal());
            preparedStatement.setInt(5, room.getTypeRoom().ordinal());
            boolean execute = preparedStatement.execute();

            // get the generated key for the id
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int id = -1;
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
        final String QUERY = "Delete FROM rooms WHERE idRoom = " + '"' + Integer.toString(room.getIdRoom()) + '"';
        try (Connection con = connector.getConnection();
             Statement query =  con.createStatement();) {
            int execute = query.executeUpdate(QUERY);
            if (execute>0) {
                System.out.println("Delete room as " + Integer.toString(room.getNumber()));
            } else {
                System.out.println("The room does not exist");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateRoom(Room room) {
        final String QUERY = "UPDATE rooms number = ?, numberOfGuests = ?, price = ?, stateRoom = ?, typeRoom = ? WHERE idRoom = ?";
        try (Connection con = connector.getConnection();) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setInt(1, room.getNumber());
            preparedStatement.setInt(2, room.getNumberOfGuests());
            preparedStatement.setInt(3, room.getPrice());
            preparedStatement.setInt(4, room.getStateRoom().ordinal());
            preparedStatement.setInt(5, room.getTypeRoom().ordinal());
            preparedStatement.setInt(6, room.getIdRoom());
            boolean execute = preparedStatement.execute();
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
                StateRoom stateRoom = StateRoom.values()[rs.getInt("StateRoom")];
                TypeRoom typeRoom = TypeRoom.values()[rs.getInt("TypeRoom")];
                Room room = new Room(number, price,numberOfGuest, typeRoom);
                room.setIdRoom(idRoom);
                room.setStateRoom(stateRoom);
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }
}
