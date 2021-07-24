package eu.senla.mysql.dao;

import eu.senla.mysql.api.IPrinter;
import eu.senla.mysql.exception.NotExistObject;
import eu.senla.mysql.model.Printer;
import eu.senla.mysql.utils.RandomString;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrinterDao implements IPrinter<Printer,Integer> {
    private final DataSource connector;

    public PrinterDao(DataSource ds) {
        connector = ds;
    }

    boolean checkCode(String code) {
        final String QUERY = "Select * from printers where code=?;";
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
    public void add(Printer item) {
        int length = 10;
        RandomString generatedString = new RandomString(length);
        //generatedString.nextString();
        while (!checkCode(generatedString.nextString())) {
            generatedString = new RandomString(length);
        }
        final String QUERY = "INSERT INTO printers (model, color, type, price) VALUES (?,?,?,?);";

        try (Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS);
            item.setModel(generatedString.nextString());
            preparedStatement.setString(1, item.getModel());
            preparedStatement.setInt(2, item.getColor());
            preparedStatement.setString(3, item.getType());
            preparedStatement.setInt(4, item.getPrice());
            preparedStatement.execute();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int code;
            if (rs.next()) {
                code = rs.getInt(1);
                item.setCode(code);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Printer item) throws NotExistObject {
        final String QUERY = "Delete FROM printers WHERE code = " + '"' + item.getCode() + '"';
        try (Connection con = connector.getConnection();
             Statement query = con.createStatement()) {
            int execute = query.executeUpdate(QUERY);
            if (execute > 0) {
                System.out.println("Delete pc as " + item.getModel());
            } else {
                throw new NotExistObject("The printer does not exist");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(Printer item) throws NotExistObject {
        final String QUERY = "UPDATE printers SET `model`=?, `color`=?, `type`=?, `price`=? WHERE `code`=?;";
        try (Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setString(1, item.getModel());
            preparedStatement.setInt(2, item.getColor());
            preparedStatement.setString(3, item.getType());
            preparedStatement.setInt(4, item.getPrice());
            preparedStatement.setInt(5, item.getCode());
            if (preparedStatement.executeUpdate() == 0)
                throw new NotExistObject("The printer does not exist");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Printer get(Integer index) {
        Printer printer = null;
        final String QUERY = "select * FROM printers WHERE code = " + '"' + index + '"';
        try (Connection con = connector.getConnection();
             Statement query = con.createStatement()) {
            ResultSet rs = query.executeQuery(QUERY);
            while (rs.next()) {
                Integer code = rs.getInt("code");
                String model = rs.getString("model");
                Byte color = rs.getByte("color");
                String type = rs.getString("type");
                Integer price = rs.getInt("price");

                printer = new Printer();
                printer.setCode(code);
                printer.setModel(model);
                printer.setColor(color);
                printer.setType(type);
                printer.setPrice(price);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return printer;

    }

    @Override
    public List<Printer> listItem() {
        final String QUERY = "select * from printers";
        List<Printer> printers = new ArrayList<>();
        try (Connection con = connector.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(QUERY)) {
            while (rs.next()) {
                Integer code = rs.getInt("code");
                String model = rs.getString("model");
                Byte color = rs.getByte("color");
                String type = rs.getString("type");
                Integer price = rs.getInt("price");

                Printer printer = new Printer();
                printer.setCode(code);
                printer.setModel(model);
                printer.setColor(color);
                printer.setType(type);
                printer.setPrice(price);
                printers.add(printer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return printers;
    }
}
