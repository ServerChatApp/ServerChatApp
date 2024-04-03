package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private Connection connection;

    // TODO IMPLEMENT dotenv and set variables on .env <-------
    private String DB_DRIVER = "org.mariadb.jdbc.Driver";
    private String DB_HOST = "localhost";
    private String DB_PORT = "3306";
    private String DB_NAME = "database_name";
    private String DB_USER = "user";
    private String DB_PASSWORD = "password";


    public Database() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/database_name",
                "user", "password"
        );
    }

    private StringBuilder getConnectionString() {
        StringBuilder connectionString = new StringBuilder();
        connectionString.append(DB_DRIVER);
        connectionString.append(DB_HOST);
        connectionString.append(":");
        connectionString.append(DB_PORT);
        connectionString.append("/");
        connectionString.append(DB_NAME);
        connectionString.append("?");
        connectionString.append("user=");
        connectionString.append(DB_USER);
        connectionString.append("&");
        connectionString.append("password=");
        connectionString.append(DB_PASSWORD);
        connectionString.append("&useSSL=false&allowPublicKeyRetrieval=true");
        return connectionString;
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
}
