package gr.bibliotech.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;




/**
 * User Class.
 * Represents a User.
 * Used to Handle Operations and transactions
 * that concern the platforms' Users.
 * 
 * 
 * 
 * @version 1.0
 * @author Dimitris Papathanasiou, Hlias Mpourdakos
 */
public class User {

    private int ID;
    private String userName;
    private String email;
    private String password;

    /**
     * @param ID
     * @param userName
     * @param password
     * @param email
     */
    public User(int ID, String userName, String email, String password) {
        this.ID = ID;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    /*
     * Getters
     */

    public int getID() {
        return ID;
    }
    
    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    /*
     * No Setters Allowed
     */
}
