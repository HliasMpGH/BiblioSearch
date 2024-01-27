package gr.bibliotech;

import java.util.Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.openqa.selenium.chrome.ChromeDriver;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        if (args.length != 0 && args[0].equals("open")) {
            new ChromeDriver().get("http://localhost:8080/");
        }


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
