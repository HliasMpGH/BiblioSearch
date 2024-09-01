package gr.bibliotech.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

import gr.bibliotech.PropertyHandler;
import gr.bibliotech.web.Server;
import gr.bibliotech.error.ServerException;

public class ServerHandler extends Application {

    /** the main root that will hold all the components */
    private VBox root;

    /** the visual of the current version of the app */
    private Label versionNumber;

    /** the visual of the running status of server */
    private Circle statusColor;

    /** the interaction buttons */
    private Button startButton;
    private Button openButton;
    private Button endButton;

    /** the area used for information logging */
    private static TextArea infoArea;

    /** Handler Attribute Initialization */
    private String handlerName = PropertyHandler.getCProperty("app.handler.name");
    private String handlerVersion = PropertyHandler.getCProperty("app.version");
    private String handlerImg = PropertyHandler.getCProperty("app.handler.img");


    /**
     * Opens the Main Window that Represents the Handler 
     * @param args
     */
    public static void show(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        /* component initialization */
        root = new VBox();

        statusColor = new Circle(10);
        statusColor.setFill(Color.RED); // server is inactive on application boot up

        startButton = new Button("Start Local Server");
        openButton = new Button("Open Local Server");
        endButton = new Button("End Local Server  ");

        infoArea = new TextArea();
        infoArea.setEditable(false);
        infoArea.setFocusTraversable(false);
        infoArea.setMouseTransparent(true);
        infoArea.setWrapText(true);

        versionNumber = new Label("Version: " + handlerVersion);

        /* button actions */

        // configure the server boot up button
        startButton.setOnAction(e -> {

            // check for port availability
            if (!Server.isLive() && !Server.isPortAvailable()) {
                logWarning("Port is already in use. Cant start server.");
                return;
            }

            setButtonsStatus(false); // dont allow user interaction during boot up of server

            if (!Server.isLive()) {
                logTextln("Starting Server...");
            }

            try {
                Server.start();

                logTextln("Server Started");

                // update visual of status
                statusColor.setFill(Color.GREEN);
            } catch (ServerException ex) {
                // server is already running
                logWarning(ex.getMessage());
            } catch (Exception ex) {
                // something went wrong with server boot up
                logWarning(
                    "Problem While Starting Server: " + ex.getMessage() + "\n"
                );
            }
            setButtonsStatus(true);
        });

        // configure the server kill button
        endButton.setOnAction(e -> {
            try {
                Server.stop();

                logTextln("Server Stopped");

                // update visual of status
                statusColor.setFill(Color.RED);
            } catch (ServerException ex) {
                // server is not running
                logWarning(ex.getMessage());
            }
        });

        // configure the server opening button
        openButton.setOnAction(e -> {
            try {
                Server.open();
            } catch (ServerException ex) {
                // server is not running
                logWarning(ex.getMessage());
            } catch (IOException ex) {
                // something went wrong with browser boot up
                logWarning(
                    "Problem While Opening Browser: " + ex.getMessage() + "\n"
                );
            }
        });


        // add all the components to the root and set the layout
        root.getChildren().addAll(statusColor, startButton, openButton, endButton, infoArea, versionNumber);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);

        // add the root to the scene and visualize the main stage
        primaryStage.setScene(new Scene(root, 300, 300, Color.BLACK));
        primaryStage.setTitle(handlerName);
        primaryStage.getIcons().add(new Image(handlerImg));
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e -> {
            try {
                Server.stop();
            } catch (ServerException ex) {}
        }); // kill the server upon application exit - important
        primaryStage.show();
    }

    /**
     * logs a message to the UI area
     * @param text the message to log
     */
    public static void logText(String text) {
        infoArea.appendText(text);
    }

    /**
     * logs a message to the UI area, following a new line
     * @param text the message to log
     */
    public static void logTextln(String text) {
        logText(text + "\n");
    }

    /**
     * logs a message to the UI area. Used for warning messages
     * @param warning the warning message to log
     */
    public static void logWarning(String warning) {
        logTextln(warning);
    }

    /** 
     * Handles the Active Setting of the Buttons
     * @param active the boolean value used to set
     * the functionality of the buttons
     */
    public void setButtonsStatus(boolean active) {
        startButton.setDisable(!active);
        openButton.setDisable(!active);
        endButton.setDisable(!active);
    }
}
