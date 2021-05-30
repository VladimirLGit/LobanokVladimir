package eu.senla.Hotel.dao;

import eu.senla.Hotel.api.dao.IServiceDao;
import eu.senla.Hotel.model.Service;


import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;

public class ServiceDao implements IServiceDao {
    private Connector connector;

    public ServiceDao() {
        connector = new Connector();
    }
    @Override
    public void addService(Service service) {
        final String QUERY = "INSERT INTO guests (Name, Price) VALUES (?,?)";
        //return idRoom into ?;
        try (Connection con = connector.getConnection();){
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setString(1, service.getNameService());
            preparedStatement.setInt(2, service.getPriceService());
            boolean execute = preparedStatement.execute();

            // get the generated key for the id
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int id = -1;
            if (rs.next()) {
                id = rs.getInt(1);
                service.setIdService(id);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteService(Service service) {
        final String QUERY = "Delete FROM services WHERE idService = " + '"' + Integer.toString(service.getIdService()) + '"';
        try (Connection con = connector.getConnection();
             Statement query =  con.createStatement();) {
            int execute = query.executeUpdate(QUERY);
            query.close();
            if (execute>0) {
                System.out.println("Delete service as " + service.getNameService());
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
        try (Connection con = connector.getConnection();) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setString(1, service.getNameService());
            preparedStatement.setInt(2, service.getPriceService());
            preparedStatement.setInt(3, service.getIdService());
            boolean execute = preparedStatement.execute();
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
                service.setIdService(idService);
                services.add(service);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }
}
