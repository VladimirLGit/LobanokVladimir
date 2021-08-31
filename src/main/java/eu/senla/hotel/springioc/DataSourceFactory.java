package eu.senla.hotel.springioc;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DataSourceFactory {
    private String db;
    private String username;
    private String password;
    private String host;

    MysqlDataSource dataSource = new MysqlDataSource();

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public DataSource getDataSource() throws SQLException {
        dataSource.setDatabaseName(db);
        dataSource.setServerName(host);
        dataSource.setPort(3306);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setServerTimezone("UTC");
        return dataSource;
    }
}
