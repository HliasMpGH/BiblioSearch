package gr.bibliotech.data;

import java.util.List;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.dao.EmptyResultDataAccessException;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;

import gr.bibliotech.error.InvalidInfoException;
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
                        "username = ? AND password = ?";

        try {
            User user = jdbcTemplate.queryForObject(
                query,
                new UserMapper(),
                username,
                getHash(password)  // check if the hashes match
            );
            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("No Match Between Username and Password Found!");
        }
    }

    /**
     * Registers a new user in the DB
     * @param username new users' username
     * @param email new users' email
     * @param password new users' password
     */
    public void registerUser(String username, String email, String password) {

        String insert = "INSERT INTO USERS(username, email, password) " +
                        "VALUES (?, ?, ?)";

        jdbcTemplate.update(insert, username, email, getHash(password)); // save the hash of the password
    }

    /**
     * Checks a usernames' spell validity
     * @param username the username to check
     * @return true if its valid; false otherwise
     */
    public boolean isValidUserName(String username) {
        if (username != null) {
            return username.trim().length() > 4;
        }
        return false;
    }

    /**
     * Checks a passwords' strength
     * @param password the password to check
     * @return true if the password is strong; flase otherwise
     */
    public boolean isValidPassword(String password) {
        String strongPWregex = "^(?=.*[A-Z].*[A-Z])(?=.*[!@#$&*])" +
                               "(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*" +
                               "[a-z]).{8}$";

        Pattern strongPassword = Pattern.compile(strongPWregex);

        return strongPassword.matcher(password).matches();
    }

    /**
     * Validates a username
     * @param username
     * @throws InvalidInfoException if the username is not valid
     */
    public void validateUsername(String username) {
        if (!isValidUserName(username)) {
            throw new InvalidInfoException("Invalid Username. " +
                "Usernames Must be Longer than 8 Characters!", "Username");
        }

        if (existsUserName(username)) {
            throw new InvalidInfoException("The username You Provided Already Exists! " +
                "Please Choose a Different One!", "Username");
        }
    }

    /**
     * Validates a password
     * @param password
     * @throws InvalidInfoException if the password is not valid
     */
    public void validatePassword(String password) {
        if (!isValidPassword(password)) {
            throw new InvalidInfoException(
                "Weak Password. Passwords Must Obey the Following rules:" +
                "must have two uppercase letters." +
                "must have one special case letter." +
                "must have two digits." +
                "must have three lowercase letters." +
                "is of length 8.",
                "Password"
            );
        }
    }

    /**
     * Validates an email
     * @param email
     * @throws InvalidInfoException if the email is not valid
     */
    public void validateEmail(String email) {
        if (existsUserMail(email)) {
            throw new InvalidInfoException("The email You Provided Already Exists! " +
                "Please Choose a Different One!", "Email");
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
