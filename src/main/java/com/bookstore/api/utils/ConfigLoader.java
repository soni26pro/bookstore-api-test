package com.bookstore.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class to load configuration properties from the {@code config.properties} file
 * located in the classpath. This is typically used to retrieve environment-specific values
 * like base URLs, tokens, or timeout settings.
 */
public class ConfigLoader {

    /** Holds the loaded configuration properties. */
    private static Properties properties;

    /** The name of the properties file to load from the classpath. */
    private static final String CONFIG_FILE = "config.properties";

    // Static block to initialize the properties file when the class is first loaded
    static {
        properties = new Properties();
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                System.err.println("Sorry, unable to find " + CONFIG_FILE);
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Retrieves the value of a given property key from the configuration file.
     *
     * @param key the name of the property to retrieve
     * @return the value of the property, or {@code null} if the key is not found
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
