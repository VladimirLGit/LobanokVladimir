package eu.senla.mysql.dao;

import eu.senla.mysql.api.IProduct;
import eu.senla.mysql.exception.NotExistObject;
import eu.senla.mysql.model.Product;


import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao implements IProduct<Product,String> {
    private final DataSource connector;

    public ProductDao(DataSource ds) {
        connector = ds;
    }

    boolean checkCode(String code) {
        final String QUERY = "Select * from products where code=?;";
        try (Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setString(1, code);
            return preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public void add(Product item) {
        final String QUERY = "INSERT INTO products (maker, model, type) VALUES (?,?,?);";

        try (Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setString(1, item.getMaker());
            preparedStatement.setString(2, item.getModel());
            preparedStatement.setString(3, item.getType());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Product item) throws NotExistObject {
        final String QUERY = "Delete FROM products WHERE code = " + '"' + item.getModel() + '"';
        try (Connection con = connector.getConnection();
             Statement query = con.createStatement()) {
            int execute = query.executeUpdate(QUERY);
            if (execute > 0) {
                System.out.println("Delete product as " + item.getModel());
            } else {
                throw new NotExistObject("The product does not exist");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(Product item) throws NotExistObject {
        final String QUERY = "UPDATE products SET `maker`=?, `type`=? WHERE `model`=?;";
        try (Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setString(1, item.getMaker());
            preparedStatement.setString(2, item.getType());
            preparedStatement.setString(3, item.getModel());
            if (preparedStatement.executeUpdate() == 0)
                throw new NotExistObject("The product does not exist");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Product get(String model) {
        Product product = null;
        final String QUERY = "select * FROM products WHERE model = " + '"' + model + '"';
        try (Connection con = connector.getConnection();
             Statement query = con.createStatement()) {
            ResultSet rs = query.executeQuery(QUERY);
            while (rs.next()) {
                String maker = rs.getString("maker");
                String type = rs.getString("type");

                product = new Product();
                product.setMaker(maker);
                product.setModel(model);
                product.setType(type);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;

    }

    @Override
    public List<Product> listItem() {
        final String QUERY = "select * from products";
        List<Product> products = new ArrayList<>();
        try (Connection con = connector.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(QUERY)) {
            while (rs.next()) {
                String maker = rs.getString("maker");
                String model = rs.getString("model");
                String type = rs.getString("type");

                Product product = new Product();
                product.setMaker(maker);
                product.setModel(model);
                product.setType(type);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}