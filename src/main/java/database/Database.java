package database;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private Connection connection;

    private String DB_DRIVER = "jdbc:postgresql://";
    private String DB_HOST;
    private String DB_PORT;
    private String DB_NAME;
    private String DB_USER;
    private String DB_PASSWORD;
    private String DB_SSL_MODE;

    public Database() throws SQLException {
        loadEnvVariables();
        connection = DriverManager.getConnection(getConnectionString().toString());
    }

    private void loadEnvVariables() {
        Dotenv dotenv = Dotenv.load();
        DB_HOST = dotenv.get("DB_HOST");
        DB_PORT = dotenv.get("DB_PORT");
        DB_NAME = dotenv.get("DB_NAME");
        DB_USER = dotenv.get("DB_USER");
        DB_PASSWORD = dotenv.get("DB_PASSWORD");
        DB_SSL_MODE = dotenv.get("DB_SSL_MODE");
    }

    private String getConnectionString() {
        return DB_DRIVER + DB_HOST + ":" + DB_PORT + "/" + DB_NAME +
                "?user=" + DB_USER + "&password=" + DB_PASSWORD + "&sslmode=" + DB_SSL_MODE;
    }

    public void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Getters & Setters

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getDB_DRIVER() {
        return DB_DRIVER;
    }

    public void setDB_DRIVER(String DB_DRIVER) {
        this.DB_DRIVER = DB_DRIVER;
    }

    public String getDB_HOST() {
        return DB_HOST;
    }

    public void setDB_HOST(String DB_HOST) {
        this.DB_HOST = DB_HOST;
    }

    public String getDB_PORT() {
        return DB_PORT;
    }

    public void setDB_PORT(String DB_PORT) {
        this.DB_PORT = DB_PORT;
    }

    public String getDB_NAME() {
        return DB_NAME;
    }

    public void setDB_NAME(String DB_NAME) {
        this.DB_NAME = DB_NAME;
    }

    public String getDB_USER() {
        return DB_USER;
    }

    public void setDB_USER(String DB_USER) {
        this.DB_USER = DB_USER;
    }

    public String getDB_PASSWORD() {
        return DB_PASSWORD;
    }

    public void setDB_PASSWORD(String DB_PASSWORD) {
        this.DB_PASSWORD = DB_PASSWORD;
    }

    public String getDB_SSL_MODE() {
        return DB_SSL_MODE;
    }

    public void setDB_SSL_MODE(String DB_SSL_MODE) {
        this.DB_SSL_MODE = DB_SSL_MODE;
    }
}
