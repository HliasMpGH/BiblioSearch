package gr.bibliotech.data;

import java.util.ArrayList;
import java.util.List;

import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.dao.EmptyResultDataAccessException;


import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;

import gr.bibliotech.app.User;

@Repository
public class UserDAO {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * Retrives the Registered Users from the Data Base
     * @return a list of users
     */
    public List<User> getUsers() {
        List<User> users = jdbcTemplate.query("SELECT * FROM USERS", new UserMapper());

        return users;
    }

    /**
     * Checks a usernames' existance in the platform.
     * Should be Used Before Creating new User.
     * @param username the username to check
     * @return true if the username is registered in the platform, false otherwise
     */
    public boolean existsUserName(String username) {
        String query = "SELECT COUNT(*) > 0 FROM USERS WHERE username = ?";

        Boolean existsName = jdbcTemplate.queryForObject(query, Boolean.class, username);

        return existsName;
    }

    /**
     * Checks an emails' existance in the platform.
     * Should be Used Before Creating new User.
     * @param email the email to check
     * @return true if the email is registered in the platform, false otherwise
     */
    public boolean existsUserMail(String email) {
        String query = "SELECT COUNT(*) > 0 FROM USERS WHERE email = ?";

        Boolean existsMail = jdbcTemplate.queryForObject(query, Boolean.class, email);

        return existsMail;
    }

    /**
     * Used to Authenticate a users' Credentials upon Signing In 
     * @param username the username to match
     * @param password the password to match with association to the username
     * @return the user that matches the username & password association
     */
    public User authenticate(String username, String password) {

        String query = "SELECT * FROM USERS WHERE " +
                        "username = ? AND email = ?";

        try {
            User user = jdbcTemplate.queryForObject(query, new UserMapper(), username, getHash(password));
            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("No Match Between Username and Password Found!");
        }
    }

    /**
     * Returns the User with Said ID.
     * Useful for Associations Between Data
     * @param id the id of the user to return
     * @return the User with Said id
     * @throws Exception if a match between a user and the ID 
     * could not be resolved
     */
    public User getUser(int id) {
        String query = "SELECT * FROM USERS WHERE idUser = ?";

        try {
            User user = jdbcTemplate.queryForObject(query, new UserMapper(), id);
            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("The ID " + id + "Could not be Resolved to a User");
        }
    }

    public boolean isValidUserName(String username) {
        if (username != null) {
            return username.trim().length() > 4;
        }
        return false;
    }

    public boolean isValidEmail(String email) {
        // empty for now
        return true;
    }

    public boolean isValidPassword(String password) {
        String strongPWregex = "^(?=.*[A-Z].*[A-Z])(?=.*[!@#$&*])" +
                               "(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*" +
                               "[a-z]).{8}$";

        Pattern strongPassword = Pattern.compile(strongPWregex);

        return strongPassword.matcher(password).matches();
    }

    public void validateUsername(String username) {
        if (!isValidUserName(username)) {
            throw new RuntimeException("Invalid Username. " +
                "Usernames Must be Longer than 8 Characters!");
        }

        if (existsUserName(username)) {
            throw new RuntimeException("The username You Provided Already Exists! " +
                "Please Choose a Different One!");
        }
    }

    public void validatePassword(String password) {
        if (!isValidPassword(password)) {
            throw new RuntimeException(
                "Weak Password. Passwords Must Obey the Following rules:<br>" +
                "must have two uppercase letters<br>" +
                "must have one special case letter<br>" +
                "must have two digits<br>" +
                "must have three lowercase letters<br>" +
                "is of length 8"
            );
        }
    }

    public void validateEmail(String email) {
        if (!isValidEmail(email)) {
            throw new RuntimeException("Email is not Valid.");
        }

        if (existsUserMail(email)) {
            throw new RuntimeException("The email You Provided Already Exists! " +
                "Please Choose a Different One!");
        }
    }

    /**
     * Used to Hash a String.
     * Should be used when handling sensitive data.
     *
     * @param string the String to be hashed
     * @return the hash value of the String
     */
    public static String getHash(String string) {
            return Hashing.sha256()
                    .hashString(string, StandardCharsets.UTF_8)
                    .toString();
    }
}
