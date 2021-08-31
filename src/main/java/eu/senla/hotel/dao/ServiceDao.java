package eu.senla.hotel.dao;

import eu.senla.hotel.api.dao.IServiceDao;
import eu.senla.hotel.exception.NotExistObject;
import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.ServiceOrder;


import javax.sql.DataSource;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class ServiceDao implements IServiceDao {
    private final DataSource connector;

    public ServiceDao(DataSource ds) {
        connector = ds;
    }

    @Override
    public void addService(ServiceOrder serviceOrder) {
        final String QUERY = "INSERT INTO services (Name, Price) VALUES (?,?)";
        //return idRoom into ?;
        try (Connection con = connector.getConnection()){
            PreparedStatement preparedStatement = con.prepareStatement(QUERY,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, serviceOrder.getName());
            preparedStatement.setInt(2, serviceOrder.getPrice());
            preparedStatement.execute();

            // get the generated key for the id
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int id;
            if (rs.next()) {
                id = rs.getInt(1);
                serviceOrder.setId(id);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteService(ServiceOrder serviceOrder) {
        final String QUERY = "Delete FROM services WHERE idService = " + '"' + serviceOrder.getId() + '"';
        try (Connection con = connector.getConnection();
             Statement query =  con.createStatement()) {
            int execute = query.executeUpdate(QUERY);
            query.close();
            if (execute>0) {
                System.out.println("Delete service as " + serviceOrder.getName());
            } else {
                System.out.println("The service does not exist");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateService(ServiceOrder serviceOrder) {
        final String QUERY = "UPDATE services name = ?, price = ? WHERE idService = ?";
        try (Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setString(1, serviceOrder.getName());
            preparedStatement.setInt(2, serviceOrder.getPrice());
            preparedStatement.setInt(3, serviceOrder.getId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    public List<ServiceOrder> allServices() {
        final String QUERY = "select * from services";
        List<ServiceOrder> serviceOrders = new ArrayList<>();
        try(Connection con = connector.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY)) {
            stmt.close();
            while(rs.next()){
                int idService = rs.getInt("IDService");
                String nameService = rs.getString("Name");
                int price = rs.getInt("Price");
                ServiceOrder serviceOrder = new ServiceOrder(nameService, price);
                serviceOrder.setId(idService);
                serviceOrders.add(serviceOrder);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviceOrders;
    }


    public void addOrderGuest(Guest guest, ServiceOrder serviceOrder) {
        final String QUERY = "INSERT INTO linkService (idGuest, idService) VALUES (?,?)";
        //return idRoom into ?;
        try (Connection con = connector.getConnection()){
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setInt(1, guest.getId());
            preparedStatement.setInt(2, serviceOrder.getId());
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
    public ServiceOrder readService(Integer idService) {
        final String QUERY = "select * from services WHERE IDService = ?;";
        ServiceOrder serviceOrder = null;
        try (Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setInt(1, idService);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("IDService");
                String name = rs.getString("Name");
                int price = rs.getInt("Price");
                serviceOrder = new ServiceOrder(name, price);
                serviceOrder.setId(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviceOrder;
    }

    @Override
    public void setServices(List<ServiceOrder> serviceOrders) {

    }
}
