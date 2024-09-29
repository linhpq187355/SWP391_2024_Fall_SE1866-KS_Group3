package com.homesharing.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * The Config class is a utility class that loads configuration properties
 * from a properties file and provides access to these properties.
 * This class cannot be instantiated.
 */
public class Config {

    private static final Logger logger = LoggerFactory.getLogger(Config.class); // Logger instance

    // Private constructor to prevent instantiation
    private Config() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    private static final Properties properties = new Properties();

    static {
        // Load properties from the config.properties file
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);// Load properties if the file is found
            }
        } catch (Exception e) {
            // Log an error if the properties file cannot be loaded
            logger.error("Error in get config properties: {}", e.getMessage(), e);
        }
    }
    /**
     * Retrieves the base URL from the properties.
     *
     * @return the base URL as a String, or null if not found
     */
    public static String getBaseUrl() {
        return properties.getProperty("base.url");
    }
}
