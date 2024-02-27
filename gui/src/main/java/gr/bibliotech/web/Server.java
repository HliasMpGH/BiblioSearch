package gr.bibliotech.web;

import java.net.ConnectException;
import java.net.Socket;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import gr.bibliotech.PropertyHandler;
import gr.bibliotech.gui.App;

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
    public static boolean start() {
        if (!running) {
            try {
                ctx = SpringApplication.run(Server.class, ""); // override CLI arguments
                return running = true;
            } catch (Exception e) {
                App.logWarning("Error while Starting Server: " + e.getMessage() + "\n");
            }
        }
        return false;
    }

    /**
     * Kills the Server
     * @return true if the server was running; false otherwise
     */
    public static boolean stop() {
        if (running) {
            ctx.close();
            return !(running = false);
        }
        return false;
    }

    /**
     * Opens the Connection in the default browser
     * @return true if the server is running; false otherwise
     */
    public static boolean open() {
        if (running) {
            Runtime rt = Runtime.getRuntime();
            try {
                rt.exec("rundll32 url.dll,FileProtocolHandler " + location);
                return true;
            } catch (Exception e) {
                App.logWarning("Error While Opening Browser");
            }
        }
        return false;
    }

    /**
     * Checks if the port is already being used in this machine
     * @return true if the port is available for use by the local 
     * server; false otherwise
     */
    public static boolean isPortAvailable() {
        try (Socket socket = new Socket(host, port)) {
            return false;
        } catch (ConnectException e) {
            return true;
        } catch (IOException e) {
            return false;
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
