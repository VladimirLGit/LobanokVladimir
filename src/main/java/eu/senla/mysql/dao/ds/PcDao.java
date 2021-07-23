package eu.senla.mysql.dao.ds;

import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.StateGuest;
import eu.senla.hotel.utils.DateUtils;
import eu.senla.mysql.api.IPc;
import eu.senla.mysql.exception.NotExistObject;
import eu.senla.mysql.model.PC;
import eu.senla.mysql.utils.RandomString;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PcDao implements IPc<PC> {
    private final DataSource connector;

    public PcDao(DataSource ds) {
        connector = ds;
    }

    boolean checkCode(String code) {
        final String QUERY = "Select * from pcs where code=?;";
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
    public void add(PC item) {
        int length = 10;
        RandomString generatedString = new RandomString(length);
        //generatedString.nextString();
        while (!checkCode(generatedString.nextString())) {
            generatedString = new RandomString(length);
        }
        final String QUERY = "INSERT INTO pcs (model, speed, ram, hd, cd, price) VALUES (?,?,?,?,?,?,?);";

        try (Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, generatedString.nextString());
            preparedStatement.setInt(2, item.getSpeed());
            preparedStatement.setDouble(3, item.getRam());
            preparedStatement.setDouble(4, item.getHd());
            preparedStatement.setInt(5, item.getCd());
            preparedStatement.setInt(6, item.getPrice());
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
    public void delete(PC item) {
        final String QUERY = "Delete FROM pcs WHERE code = " + '"' + item.getCode() + '"';
        try (Connection con = connector.getConnection();
             Statement query = con.createStatement()) {
            int execute = query.executeUpdate(QUERY);
            if (execute > 0) {
                System.out.println("Delete pc as " + item.getModel());
            } else {
                throw new NotExistObject("The pc does not exist");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(PC item) {
        final String QUERY = "UPDATE pcs SET `model`=?, `speed`=?, `ram`=?, `hd`=?, `cd`=?, `price`=? WHERE `code`=?;";
        try (Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setString(1, item.getModel());
            preparedStatement.setInt(2, item.getSpeed());
            preparedStatement.setDouble(3, item.getRam());
            preparedStatement.setDouble(4, item.getHd());
            preparedStatement.setInt(5, item.getCd());
            preparedStatement.setInt(6, item.getPrice());
            preparedStatement.setInt(7, item.getCode());
            if (preparedStatement.executeUpdate() == 0)
                throw new NotExistObject("The pc does not exist");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public PC get(int index) {
        PC pc = null;
        final String QUERY = "select * FROM pcs WHERE code = " + '"' + index + '"';
        try (Connection con = connector.getConnection();
             Statement query = con.createStatement()) {
            ResultSet rs = query.executeQuery(QUERY);
            while (rs.next()) {
                Integer code = rs.getInt("code");
                String model = rs.getString("model");
                Integer speed = rs.getInt("speed");
                Double ram = rs.getDouble("ram");
                Double hd = rs.getDouble("hd");
                Integer cd = rs.getInt("cd");
                Integer price = rs.getInt("price");

                pc = new PC();
                pc.setCode(code);
                pc.setModel(model);
                pc.setSpeed(speed);
                pc.setRam(ram);
                pc.setHd(hd);
                pc.setCd(cd);
                pc.setPrice(price);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pc;

    }

    @Override
    public List<PC> listItem() {
        final String QUERY = "select * from pcs";
        List<PC> pcs = new ArrayList<>();
        try (Connection con = connector.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(QUERY)) {
            while (rs.next()) {
                Integer code = rs.getInt("code");
                String model = rs.getString("model");
                Integer speed = rs.getInt("speed");
                Double ram = rs.getDouble("ram");
                Double hd = rs.getDouble("hd");
                Integer cd = rs.getInt("cd");
                Integer price = rs.getInt("price");

                PC pc = new PC();
                pc.setCode(code);
                pc.setModel(model);
                pc.setSpeed(speed);
                pc.setRam(ram);
                pc.setHd(hd);
                pc.setCd(cd);
                pc.setPrice(price);
                pcs.add(pc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pcs;
    }
}
