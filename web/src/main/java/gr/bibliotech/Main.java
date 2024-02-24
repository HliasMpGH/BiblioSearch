package gr.bibliotech;

import java.util.Properties;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.openqa.selenium.chrome.ChromeDriver;

@SpringBootApplication
public class Main {

    private static ConfigurableApplicationContext ctx;
    public static void main(String[] args) {
        start(args);

        if (args.length != 0 && args[0].equals("open")) {
            new ChromeDriver().get("http://localhost:8080/books");
        }

        Scanner sc = new Scanner(System.in);
        if (sc.nextLine().equals("stop")) {
            stop();
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

    public static void start(String[] args) {
        ctx = SpringApplication.run(Main.class, args);
    }

    public static void stop() {
        ctx.close();
    }  
}
