import client.ClientManager;
import database.Database;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ClientManagerTest {

    private static final String USERNAME = "daniel";
    private static final String PASSWORD = "Daniel1234.";
    private static Database database;

    @BeforeAll
    public static void setUpClass() {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    @AfterAll
    public static void tearDownClass() {
        database.closeConnection();
    }

    @BeforeEach
    public void setUp() {
        ensureUserDeleted();
    }

    @AfterEach
    public void tearDown() {
        deleteUserIfExists();
    }

    @Test
    public void testRegisterNewUser() {
        assertTrue(ClientManager.register(USERNAME, PASSWORD));
    }

    @Test
    public void testRegisterExistingUser() {
        // Registering the same user twice should fail
        ClientManager.register(USERNAME, PASSWORD);
        assertFalse(ClientManager.register(USERNAME, PASSWORD));
    }

    private void ensureUserDeleted() {
        try {
            Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE username = ?");
            statement.setString(1, USERNAME);
            statement.executeUpdate();
            // No cierres la conexión aquí
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void deleteUserIfExists() {
        try {
            Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE username = ?");
            statement.setString(1, USERNAME);
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
