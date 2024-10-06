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

    import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * The {@code Config} class is a utility class responsible for loading and providing access to configuration
 * properties, such as the base URL, from the {@code config.properties} file.
 * <p>
 * This class cannot be instantiated and all methods are static.
 * @author ManhNC
 */
public class Config {

    // Logger instance for logging error messages and other events.
    private static final Logger logger = LoggerFactory.getLogger(Config.class);

    /**
     * Private constructor to prevent instantiation of this utility class.
     * Attempting to instantiate this class will result in an {@code UnsupportedOperationException}.
     *
     * @throws UnsupportedOperationException if the class is instantiated.
     */
    private Config() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // Properties object to hold configuration values loaded from the properties file.
    private static final Properties properties = new Properties();

    // Static initializer block to load the properties file during class loading.
    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage(), e);
        }
    }

    /**
     * Retrieves the base URL from the configuration properties.
     *
     * @return the base URL as a {@code String}, or {@code null} if the property is not found.
     */
    public static String getBaseUrl() {
        return properties.getProperty("base.url");
    }
}
