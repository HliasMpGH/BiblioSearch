package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Connect {

    public static Connection enableConnection() {
    
        Connection connection = null;
        String url = "jdbc:sqlite:database.db";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            System.out.println("Connection is successful!");

        } catch (SQLException e) {
            System.err.println("Error connecting to the database.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return connection;
    }

    public static void main(String[] arsg) {
        Properties properties = PropertyHandler.getConfig();

        System.out.println("App Name:");
        System.out.println(properties.getProperty("app.name"));
        System.out.println("App Version:");
        System.out.println(properties.getProperty("app.version"));
        System.out.println("App Creator:");
        System.out.println(properties.getProperty("app.developer"));
        System.out.println("CLI Property:");
        System.out.println(System.getenv("SECRET_KEY"));


        /*
        Connection connection = enableConnection();

        String query = "Select * from Users";

        try (Statement stm = connection.createStatement()) {
            ResultSet rs = stm.executeQuery(query);
            System.out.println("CURRENT DATA");
            while (rs.next()) {
                
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("done!");
        */
    }
}