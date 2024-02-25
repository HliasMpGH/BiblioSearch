package gr.bibliotech.data;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

        User user = jdbcTemplate.queryForObject(query, new UserMapper(), username, getHash(password));

        if (user != null) {
            return user;
        }

        throw new RuntimeException("No Such User!");
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

        User user = jdbcTemplate.queryForObject(query, new UserMapper(), id);

        return user;
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
