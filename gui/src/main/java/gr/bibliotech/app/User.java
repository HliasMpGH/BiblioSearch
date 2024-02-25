package gr.bibliotech.app;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;


//import com.google.common.hash.Hashing;


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

    /**
     * Used to Hash a String.
     * Should be used when handling sensitive data.
     * 
     * @param string the String to be hashed
     * @return the hash value of the String
     
    public static String getHash(String string) {
            return Hashing.sha256()
                    .hashString(string, StandardCharsets.UTF_8)
                    .toString();
    }*/
}
