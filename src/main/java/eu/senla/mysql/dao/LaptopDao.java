package eu.senla.mysql.dao;

import eu.senla.mysql.api.ILaptop;
import eu.senla.mysql.exception.NotExistObject;
import eu.senla.mysql.model.Laptop;
import eu.senla.mysql.model.PC;
import eu.senla.mysql.utils.RandomString;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LaptopDao implements ILaptop<Laptop,Integer> {
    private final DataSource connector;

    public LaptopDao(DataSource ds) {
        connector = ds;
    }

    boolean checkCode(String code) {
        final String QUERY = "Select * from laptops where code=?;";
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
        public void add(Laptop item) {
        int length = 10;
        RandomString generatedString = new RandomString(length);
        //generatedString.nextString();
        while (!checkCode(generatedString.nextString())) {
            generatedString = new RandomString(length);
        }
        final String QUERY = "INSERT INTO laptops (model, speed, ram, hd, price, screen) VALUES (?,?,?,?,?,?);";

        try (Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS);
            item.setModel(generatedString.nextString());
            preparedStatement.setString(1, item.getModel());
            preparedStatement.setInt(2, item.getSpeed());
            preparedStatement.setDouble(3, item.getRam());
            preparedStatement.setDouble(4, item.getHd());
            preparedStatement.setInt(5, item.getPrice());
            preparedStatement.setByte(6, item.getScreen());
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
        public void delete(Laptop item) throws NotExistObject {
        final String QUERY = "Delete FROM laptops WHERE code = " + '"' + item.getCode() + '"';
        try (Connection con = connector.getConnection();
             Statement query = con.createStatement()) {
            int execute = query.executeUpdate(QUERY);
            if (execute > 0) {
                System.out.println("Delete laptops as " + item.getModel());
            } else {
                throw new NotExistObject("The laptop does not exist");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

        @Override
        public void update(Laptop item) throws NotExistObject {
        final String QUERY = "UPDATE laptops SET `model`=?, `speed`=?, `ram`=?, `hd`=?, `price`=?, `screen`=? WHERE `code`=?;";
        try (Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setString(1, item.getModel());
            preparedStatement.setInt(2, item.getSpeed());
            preparedStatement.setDouble(3, item.getRam());
            preparedStatement.setDouble(4, item.getHd());
            preparedStatement.setInt(5, item.getPrice());
            preparedStatement.setInt(6, item.getScreen());
            preparedStatement.setInt(7, item.getCode());
            if (preparedStatement.executeUpdate() == 0)
                throw new NotExistObject("The laptop does not exist");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

        @Override
        public Laptop get(Integer index) {
        Laptop laptop = null;
        final String QUERY = "select * FROM laptops WHERE code = " + '"' + index + '"';
        try (Connection con = connector.getConnection();
             Statement query = con.createStatement()) {
            ResultSet rs = query.executeQuery(QUERY);
            while (rs.next()) {
                Integer code = rs.getInt("code");
                String model = rs.getString("model");
                Integer speed = rs.getInt("speed");
                Double ram = rs.getDouble("ram");
                Double hd = rs.getDouble("hd");
                Byte screen = rs.getByte("screen");
                Integer price = rs.getInt("price");

                laptop = new Laptop();
                laptop.setCode(code);
                laptop.setModel(model);
                laptop.setSpeed(speed);
                laptop.setRam(ram);
                laptop.setHd(hd);
                laptop.setScreen(screen);
                laptop.setPrice(price);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return laptop;

    }

        @Override
        public List<Laptop> listItem() {
        final String QUERY = "select * from laptops";
        List<Laptop> laptops = new ArrayList<>();
        try (Connection con = connector.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(QUERY)) {
            while (rs.next()) {
                Integer code = rs.getInt("code");
                String model = rs.getString("model");
                Integer speed = rs.getInt("speed");
                Double ram = rs.getDouble("ram");
                Double hd = rs.getDouble("hd");
                Byte screen = rs.getByte("screen");
                Integer price = rs.getInt("price");


                Laptop laptop = new Laptop();
                laptop.setCode(code);
                laptop.setModel(model);
                laptop.setSpeed(speed);
                laptop.setRam(ram);
                laptop.setHd(hd);
                laptop.setScreen(screen);
                laptop.setPrice(price);
                laptops.add(laptop);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return laptops;
    }

}
