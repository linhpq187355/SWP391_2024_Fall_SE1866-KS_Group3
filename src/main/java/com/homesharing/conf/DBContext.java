package com.homesharing.conf;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/**
 * DBContext class is responsible for managing the database connection.
 * It reads the configuration from 'dbconfig.properties' and provides
 * methods to get and close the connection.
 */
public class DBContext {


    protected DBContext(){}

    // Static connection object that holds the single connection to the database
    private static Connection connection = null;

    /**
     * Retrieves a Connection object to the database. If the connection is not
     * already established, it loads the properties from the configuration file,
     * initializes the database connection, and returns it.
     *
     * @return a Connection object to the database
     * @throws SQLException if a database access error occurs
     * @throws IOException if there is an issue reading the configuration file
     * @throws ClassNotFoundException if the JDBC driver class cannot be found
     */
    public static Connection getConnection() throws SQLException, IOException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            Properties props = new Properties();

            // Load the properties file using the class loader
            try(InputStream in = DBContext.class.getClassLoader().getResourceAsStream("dbconfig.properties")) {
                if(in == null) {
                    throw new IOException("dbconfig.properties not found");
                }
                props.load(in);
            }

            // Retrieve database connection details from the properties file
            String url = props.getProperty("db.url");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password");
            String driverClassName = props.getProperty("db.driverClassName");

            // Load the JDBC driver and establish the connection
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }

    /**
     * Closes the database connection if it is open.
     *
     * @throws SQLException if an error occurs while closing the connection
     */
    public static void closeConnection() throws SQLException {
        if((connection != null) && !connection.isClosed()) {
            connection.close();
        }
    }

}
