package gr.bibliotech;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyHandler {

    // the file of apps' configurations
    private static String configFileName = "config.xml";

    // the apps' configurations
    private static Properties configs = new Properties();

    // the ClassLoader of this class, used for accessing properties
    private static ClassLoader loader = PropertyHandler.class.getClassLoader();


    // init config memory upon application boot up
    static {
        if (loader != null) {
            try (InputStream config = loader.getResourceAsStream(configFileName)) {
                // load config file as a property
                configs.loadFromXML(config);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Apps' Configurations were Unable to Load");
        }
    }

    /**
     * @return the config file of the app as a property
     */
    public static Properties getConfig() {
        return configs;
    }

    /**
     * Retrieves a configurations' String value; based on a given key
     * @param key a configuration key
     * @return the value of said configuration or N|A
     */
    public static String getCProperty(String key) {
        return getConfig().getProperty(key, "N|A");
    }

    /**
     * Retrieves a configurations' int value; based on a given key
     * @param key a configuration key of an integer value
     * @return the value of said configuration or -1 if
     * said value is not of int value
     */
    public static int getIntProperty(String key) {
        try {
            return Integer.parseInt(getCProperty(key));
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
