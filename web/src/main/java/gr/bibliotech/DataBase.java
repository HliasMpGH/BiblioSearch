package gr.bibliotech;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {

    public static Connection getNewConnection() {

        Connection connection = null;
        String username = "root";
        String password = "root";

        String url = "jdbc:mysql://localhost:3306/bookservice";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection is successful!");

        } catch (Exception e) {
            throw new RuntimeException("Error connecting to the database.");
        }

        return connection;
    }

    /**
     * Creates the empty database
     * used by the platform
     */
    public static String create() {
        Connection connection = getNewConnection();

        try (BufferedReader reader = new BufferedReader(
                new FileReader("generated-sources/tableCreation.sql"))) {

            StringBuilder create = new StringBuilder();

            reader.lines()
                        .map(line -> line + "\n")
                        .forEach(create::append);
            
            Statement stm = connection.createStatement();
            
            stm.executeUpdate(create.toString()); // execute the creation
            stm.close();
        } catch (Exception e) {
            return e.getMessage();
          //  throw new RuntimeException(e.getMessage());
            //throw new RuntimeException("An Error Occured During Creation of DataBase");
        } finally {
            close(connection);
        }
        return "";
    }

    /**
     * Initializes the empty database
     * with the data used by the platform
     */
    public static void init() {
        Connection connection = getNewConnection();

        try (BufferedReader reader = new BufferedReader(
                new FileReader("generated-sources/tableFill.sql"))) {

            StringBuilder fill = new StringBuilder();

            reader.lines()
                        .map(line -> line + "\n")
                        .forEach(fill::append);
            
            Statement stm = connection.createStatement();
            
            stm.executeUpdate(fill.toString()); // execute the creation
            stm.close();
        } catch (Exception e) {
            throw new RuntimeException("An Error Occured During Initialization of DataBase");
        } finally {
            close(connection);
        }
    }

    /**
     * Drops the database along its data
     */
    public static void drop() {
        Connection connection = getNewConnection();

        try (BufferedReader reader = new BufferedReader(
                new FileReader("generated-sources/tableDrop.sql"))) {

            StringBuilder drop = new StringBuilder();
            
            reader.lines()
                        .map(line -> line + "\n")
                        .forEach(drop::append);

            Statement stm = connection.createStatement();
            
            int update = stm.executeUpdate(drop.toString()); // execute the drop
            stm.close();

            System.out.println(update + " tables dropped");
        } catch (Exception e) {
            throw new RuntimeException("An Error Occured During Destruction of DataBase");
        }  finally {
            close(connection);
        }
    }

    /**
     * Closes the Connection to the Database
     */
    public static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("An Error Occured During Internal Transmission");
        }
    }
}
