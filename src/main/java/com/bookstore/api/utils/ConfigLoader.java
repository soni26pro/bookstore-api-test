package com.bookstore.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.nio.file.Paths;

/**
 * Utility class to load configuration properties from a properties file
 * located in the classpath. Supports loading environment-specific configurations
 * based on a system property.
 */
public class ConfigLoader {

    /** Holds the loaded configuration properties. */
    private static Properties properties;

    /** The base name of the properties file. */
    private static final String BASE_CONFIG_FILE = "config";
    /** The file extension for properties files. */
    private static final String PROPERTIES_EXTENSION = ".properties";
    /** The system property key for specifying the environment. */
    private static final String ENV_SYSTEM_PROPERTY = "env";

    // Static block to initialize the properties file when the class is first loaded
    static {
        String env = System.getProperty(ENV_SYSTEM_PROPERTY);
        String configFileName = BASE_CONFIG_FILE + (env != null && !env.isEmpty() ? "-" + env : "") + PROPERTIES_EXTENSION;

        properties = new Properties();
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream(configFileName)) {
            if (input == null) {
                // Fallback to default config.properties if environment-specific file not found
                if (env != null && !env.isEmpty()) {
                     System.out.println("Environment-specific config file not found: " + configFileName + ". Attempting to load default config.properties");
                     try (InputStream defaultInput = ConfigLoader.class.getClassLoader().getResourceAsStream(BASE_CONFIG_FILE + PROPERTIES_EXTENSION)) {
                         if (defaultInput == null) {
                             throw new IOException("Default config file not found: " + BASE_CONFIG_FILE + PROPERTIES_EXTENSION);
                         }
                         properties.load(defaultInput);
                     }
                } else {
                    throw new IOException("Config file not found: " + configFileName);
                }
            } else {
                properties.load(input);
            }
        } catch (IOException ex) {
            // Wrap the IOException in a RuntimeException as this is a critical failure
            throw new RuntimeException("Failed to load configuration file: " + configFileName, ex);
        }
    }

    /**
     * Retrieves the value of a given property key from the configuration file.
     * Throws a RuntimeException if the key is not found.
     *
     * @param key the name of the property to retrieve
     * @return the value of the property
     * @throws RuntimeException if the key is not found in the configuration
     */
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in the configuration.");
        }
        return value;
    }

    /**
     * Sets the value of a given property key in the configuration.
     *
     * @param key the name of the property to set
     * @param value the value to set for the property
     */
    public static void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    /**
     * Saves the current properties to the configuration file.
     * This will overwrite the existing file.
     */
    public static void save() {
        String env = System.getProperty(ENV_SYSTEM_PROPERTY);
        String configFileName = BASE_CONFIG_FILE + (env != null && !env.isEmpty() ? "-" + env : "") + PROPERTIES_EXTENSION;

        // Save to the actual source file in src/test/resources
        String projectDir = System.getProperty("user.dir");
        java.nio.file.Path configPath = Paths.get(projectDir, "src", "test", "resources", configFileName);

        try (java.io.OutputStream output = new java.io.FileOutputStream(configPath.toFile())) {
            properties.store(output, null);
            System.out.println("Successfully saved properties to: " + configPath);
        } catch (Exception ex) {
            System.err.println("Failed to save configuration file: " + configPath);
            ex.printStackTrace();
            throw new RuntimeException("Failed to save configuration file: " + configFileName, ex);
        }
    }
}
