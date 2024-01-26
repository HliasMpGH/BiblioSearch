package gr.bibliotech;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

/*
        Properties properties = PropertyHandler.getConfig();

        System.out.println("App Name:");
        System.out.println(properties.getProperty("app.name"));
        System.out.println("App Version:");
        System.out.println(properties.getProperty("app.version"));
        System.out.println("App Creator:");
        System.out.println(properties.getProperty("app.developer"));
        System.out.println("CLI Property:");
        System.out.println(System.getenv("SECRET_KEY"));
*/
    }
}
