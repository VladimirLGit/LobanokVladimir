package main.java.eu.senla.hotel.dao;

import main.java.eu.senla.hotel.api.dao.IServiceDao;
import main.java.eu.senla.hotel.exception.NotExistObject;
import main.java.eu.senla.hotel.model.Guest;
import main.java.eu.senla.hotel.model.Service;


import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class ServiceDao implements IServiceDao {
    private final Connector connector;

    public ServiceDao() {
        connector = new Connector();
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
    public List<Service> allServices() {
        final String QUERY = "select * from services";
        List<Service> services = new ArrayList<>();
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

    @Override
    public Service readService(Integer idService) {
        final String QUERY = "select * from services WHERE IDService = ?;";
        Service service = null;
        try (Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setInt(1, idService);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("IDService");
                String name = rs.getString("Name");
                int price = rs.getInt("Price");
                service = new Service(name, price);
                service.setId(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return service;
    }
}
