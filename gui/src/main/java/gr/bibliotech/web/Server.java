package gr.bibliotech.web;

import java.net.Socket;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import gr.bibliotech.PropertyHandler;
import gr.bibliotech.error.ServerException;

@SpringBootApplication
public class Server {

    /** holds the running state of the server */
    private static boolean running;

    /** holds the current instance of the server */
    private static ConfigurableApplicationContext ctx;

    /** the host of the server */
    private static String host = PropertyHandler.getCProperty("app.host");

    /** the port of the server */
    private static int port = PropertyHandler.getIntProperty("app.port");

    /** the full location of the app */
    private static String location = PropertyHandler.getCProperty("app.location");


    /**
     * Boots up the Server
     * @return true if the server was not running; false otherwise
     */
    public static void start() throws ServerException {
        if (running) {
            throw new ServerException(
                "Server is Already Running. Cannot Boot up."
            );
        }
        ctx = SpringApplication.run(Server.class, ""); // override CLI arguments
        running = true;
    }

    /**
     * Kills the Server
     * @return true if the server was running; false otherwise
     */
    public static void stop() throws ServerException {
        if (!running) {
            throw new ServerException(
                "Server is Not Running. Cannot kill."
            );
        }
        SpringApplication.exit(ctx, () -> 0);
        running = false;
    }

    /**
     * Opens the Connection in the default browser
     * @return true if the server is running; false otherwise
     */
    public static void open() throws ServerException, IOException {
        if (!running) {
            throw new ServerException(
                "Server is Not Running. Cannot Open."
            );
        }
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + location);
    }

    /**
     * Checks if the port is already being used in this machine
     * @return true if the port is available for use by the local 
     * server; false otherwise
     */
    public static boolean isPortAvailable() {
        try (Socket socket = new Socket(host, port)) {
            return false;
        } catch (IOException e) {
            return true;
        }
    }

    /**
     * @return true if the server is running; false otherwise
     */
    public static boolean isLive() {
        return running;
    }

    /**
     * @return the running instance of the server
     */
    public static ConfigurableApplicationContext getInstance() {
        return ctx;
    }
}
