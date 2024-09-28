package com.homesharing.conf;

import java.io.InputStream;
import java.util.Properties;

public class Config {

    // Private constructor to prevent instantiation
    private Config() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    private static Properties properties = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("base.url");
    }
}
