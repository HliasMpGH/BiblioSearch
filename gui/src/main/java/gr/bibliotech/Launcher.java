package gr.bibliotech;

import gr.bibliotech.gui.ServerHandler;

/**
 * The Launcher of the App.
 * The starting point of the application.
 * @version 1.0
 * @author Hlias Mpourdakos
 * @see gr.bibliotech.gui.ServerHandler
 */
public class Launcher {
    public static void main(String[] args) {
        ServerHandler.show(args);
    }
}
