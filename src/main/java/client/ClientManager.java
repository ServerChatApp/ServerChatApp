package client;

import database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientManager {

    static Database database;

    static {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ClientManager() throws SQLException {
    }

    // Crear cliente y establecer inicio de sesión y registro con JPA del servidor utilizando la base de datos PostgreSQL
    public static boolean register(String username, String password) {

        try {
            // Establecer conexión con la base de datos
            Connection connection = database.getConnection();

            // Comprobar si el usuario ya existe
            PreparedStatement checkStatement = connection.prepareStatement("SELECT COUNT(*) FROM users WHERE username = ?");
            checkStatement.setString(1, username);
            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            if (count > 0) {
                // El usuario ya existe, devolver false
                return false;
            }

            // Si el usuario no existe, insertarlo en la base de datos
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
            insertStatement.setString(1, username);
            insertStatement.setString(2, password);
            insertStatement.executeUpdate();

            // Cerrar conexión
            database.closeConnection(connection);

            // Registro exitoso, devolver true
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
