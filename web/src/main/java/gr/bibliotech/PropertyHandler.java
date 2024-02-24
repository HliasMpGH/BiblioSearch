package gr.bibliotech;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyHandler {

    public static String propFileName = "config.xml";

    public static Properties getConfig() {
        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream(propFileName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return prop;
    }
}
