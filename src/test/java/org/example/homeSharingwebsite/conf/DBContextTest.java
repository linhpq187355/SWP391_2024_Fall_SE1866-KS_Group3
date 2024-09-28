package org.example.homeSharingwebsite.conf;

import com.homesharing.conf.DBContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Test class for DBContext
public class DBContextTest {

    // Logger for logging test execution
    private static final Logger LOGGER = Logger.getLogger(DBContextTest.class.getName());
    // Database connection variable
    private static Connection connection;

    /**
     * This method will be executed once before all test cases.
     * It sets up the database connection.
     */
    @BeforeAll
    public static void setup() throws SQLException, IOException, ClassNotFoundException {
        // Establish connection before running tests
        connection = DBContext.getConnection();
        LOGGER.log(Level.INFO, "Kết nối đã được thiết lập cho kiểm thử.");
    }

    /**
     * Test if the connection is established successfully.
     * Checks if the connection is not null and is open.
     */
    @Test
    void testConnection() {
        // Check that the connection is not null
        assertNotNull(connection, "Kết nối không được null");

        // Check that the connection is still open
        try {
            assertTrue(!connection.isClosed(), "Kết nối không được đóng");
            LOGGER.log(Level.INFO, "Kết nối còn mở.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi kiểm tra trạng thái kết nối: ", e);
        }
    }

    /**
     * This method will be executed once after all test cases have completed.
     * It closes the database connection.
     */
    @AfterAll
    public static void tearDown() throws SQLException {
        // Close connection after testing
        if (connection != null && !connection.isClosed()) {
            DBContext.closeConnection();
            LOGGER.log(Level.INFO, "Kết nối đã được đóng.");
        }
    }

}
