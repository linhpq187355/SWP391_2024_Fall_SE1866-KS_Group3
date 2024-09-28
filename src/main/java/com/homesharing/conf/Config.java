package com.homesharing.conf;

    import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final Logger logger = LoggerFactory.getLogger(Config.class); // Logger instance

    // Private constructor to prevent instantiation
    private Config() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage(), e);
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("base.url");
    }
}
