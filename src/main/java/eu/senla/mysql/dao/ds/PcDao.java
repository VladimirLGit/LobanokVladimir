package eu.senla.mysql.dao.ds;

import eu.senla.mysql.api.IPc;
import eu.senla.mysql.model.PC;
import eu.senla.mysql.utils.RandomString;

import javax.sql.DataSource;
import java.sql.*;
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
        private Integer code;
        private String model;
        private Short speed;
        private Short ram;
        private Float hd;
        private String cd;
        private Integer price;
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        RandomString generatedString = new RandomString(length);
        //generatedString.nextString();
        while (!checkCode(generatedString.nextString())) {
            generatedString = new RandomString(length);
        }
        final String QUERY = "INSERT INTO pcs (code, model, speed, ram, hd, cd, price) VALUES (?,?,?,?);";
        //return idRoom into ?;
        try (Connection con = connector.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setString(1, generatedString.nextString());
            preparedStatement.setString(2, item.getModel());
            preparedStatement.setInt(3, item.getSpeed());
            preparedStatement.setInt(4, guest.getState().ordinal());
            preparedStatement.execute();
            // get the generated key for the id
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int id;
            if (rs.next()) {
                id = rs.getInt(1);
                guest.setId(id);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(PC item) {

    }

    @Override
    public void update(PC item) {

    }

    @Override
    public PC get(int index) {
        return null;
    }

    @Override
    public List listItem() {
        return null;
    }
}
