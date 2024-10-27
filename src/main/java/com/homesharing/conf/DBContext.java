/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-03      1.0                 ManhNC         First Implement
 */
package com.homesharing.conf;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The {@code DBContext} class is a utility class responsible for managing the database connection.
 * It reads configuration details from the `dbconfig.properties` file and provides methods
 * to establish and close a connection to the database.
 * <p>
 * This class uses a singleton pattern to ensure only one database connection is maintained.
 * <p>
 * Bugs: None known.
 *
 * @author ManhNC
 */
public class DBContext {

    /**
     * Private constructor to prevent instantiation of this utility class.  This enforces
     * the singleton pattern by preventing direct instantiation.
     */
    protected DBContext(){ } // Private constructor

    /**
     * Retrieves a Connection object to the database. If the connection is not
     * already established or is closed, it loads the properties from the `dbconfig.properties` file,
     * initializes the database connection using the JDBC DriverManager, and returns the connection.
     * <p>
     * The `dbconfig.properties` file must contain the following properties:
     * <ul>
     *   <li>`db.url` - The JDBC URL of the database.</li>
     *   <li>`db.username` - The username for database access.</li>
     *   <li>`db.password` - The password for database access.</li>
     *   <li>`db.driverClassName` - The fully qualified name of the JDBC driver class.</li>
     * </ul>
     *
     * @return a Connection object to the database.
     * @throws SQLException           if a database access error occurs.
     * @throws IOException            if there is an issue reading the configuration file.
     * @throws ClassNotFoundException if the JDBC driver class cannot be found.
     */
    public static Connection getConnection() throws SQLException, IOException, ClassNotFoundException {
        Properties props = new Properties();

        try (InputStream in = DBContext.class.getClassLoader().getResourceAsStream("dbconfig.properties")) {
            if (in == null) {
                throw new IOException("dbconfig.properties not found");
            }

            props.load(in);
        }

        String url = props.getProperty("db.url");
        String username = props.getProperty("db.username");
        String password = props.getProperty("db.password");
        String driverClassName = props.getProperty("db.driverClassName");

        Class.forName(driverClassName);
        return DriverManager.getConnection(url, username, password); // Trả về Connection mới
    }



    /**
     * Closes the database connection if it is open.  This method checks if the connection is not null and
     * not already closed before attempting to close it. This helps to prevent potential errors.
     *
     * @throws SQLException if an error occurs while closing the connection.
     */
    public static void closeConnection(Connection connection) throws SQLException {
        if((connection != null) && !connection.isClosed()) {
            connection.close();
        }
    }
}
