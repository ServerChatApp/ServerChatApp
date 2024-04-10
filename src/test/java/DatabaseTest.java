import database.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

    private Database database;
    private Connection connection;

    @BeforeEach
    public void setUp() throws SQLException {
        database = new Database();
        connection = database.getConnection();
    }

    @AfterEach
    public void tearDown() {
        database.closeConnection(connection);
    }

    @Test
    public void testConnection() throws SQLException {
        assertNotNull(connection);
        assertTrue(connection.isValid(5));
    }

    // Agrega más métodos de prueba según sea necesario

}
