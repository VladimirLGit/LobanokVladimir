package eu.senla.hotel.dao;

import eu.senla.hotel.api.dao.IServiceDao;
import eu.senla.hotel.exception.NotExistObject;
import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.Service;


import java.sql.*;

import java.util.ArrayList;

public class ServiceDao implements IServiceDao {
    private final Connector connector;

    public ServiceDao() {
        connector = new Connector();
    }

    public void createTableServices(){
        final String QUERY ="CREATE TABLE IF NOT EXISTS services (\n" +
                " idService int(10) NOT NULL AUTO_INCREMENT,\n" +
                " name varchar(20) NOT NULL,\n" +
                " price int(10) NOT NULL,\n" +
                " PRIMARY KEY (idService)\n" +
                ");";
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteTableServices(){
        final String QUERY ="DROP TABLE services;";
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement()) {
            stmt.execute(QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    public void addService(Service service) {
        final String QUERY = "INSERT INTO services (Name, Price) VALUES (?,?)";
        //return idRoom into ?;
        try (Connection con = connector.getConnection()){
            PreparedStatement preparedStatement = con.prepareStatement(QUERY,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, service.getName());
            preparedStatement.setInt(2, service.getPrice());
            preparedStatement.execute();

            // get the generated key for the id
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int id;
            if (rs.next()) {
                id = rs.getInt(1);
                service.setId(id);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteService(Service service) {
        final String QUERY = "Delete FROM services WHERE idService = " + '"' + service.getId() + '"';
        try (Connection con = connector.getConnection();
             Statement query =  con.createStatement()) {
            int execute = query.executeUpdate(QUERY);
            query.close();
            if (execute>0) {
                System.out.println("Delete service as " + service.getName());
            } else {
                System.out.println("The service does not exist");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateService(Service service) {
        final String QUERY = "UPDATE services name = ?, price = ? WHERE idService = ?";
        try (Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setString(1, service.getName());
            preparedStatement.setInt(2, service.getPrice());
            preparedStatement.setInt(3, service.getId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    public ArrayList<Service> allServices() {
        final String QUERY = "select * from services";
        ArrayList<Service> services = new ArrayList<>();
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY)) {
            stmt.close();
            while(rs.next()){
                int idService = rs.getInt("IDService");
                String nameService = rs.getString("Name");
                int price = rs.getInt("Price");
                Service service = new Service(nameService, price);
                service.setId(idService);
                services.add(service);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }


    public void addOrderGuest(Guest guest, Service service) {
        final String QUERY = "INSERT INTO linkService (idGuest, idService) VALUES (?,?)";
        //return idRoom into ?;
        try (Connection con = connector.getConnection()){
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setInt(1, guest.getId());
            preparedStatement.setInt(2, service.getId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteOrderGuest(Guest guest) throws NotExistObject{
        if (guest.getOrderedServices().size()>0) {
            final String QUERY = "Delete FROM linkService WHERE idGuest = " + '"' + guest.getId() + '"';
            try (Connection con = connector.getConnection();
                 Statement query = con.createStatement()) {
                int execute = query.executeUpdate(QUERY);
                if (execute > 0) {
                    System.out.println("Delete orders as " + guest.getName());
                    guest.clearListOrder();
                } else {
                    throw new NotExistObject("The orders does not exist");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}
