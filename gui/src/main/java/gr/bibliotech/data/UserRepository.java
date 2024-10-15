package gr.bibliotech.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import gr.bibliotech.app.User;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * Retrieves the Registered Users from the Data Base.
     * @return a list of users.
     */
    public List<User> getUsers() {
        return jdbcTemplate.query(
            "SELECT * FROM USERS", new UserMapper()
        );
    }

    /**
     * Checks a usernames' existence in the platform.
     * Should be Used Before Creating new User.
     * @param username the username to check
     * @return true if the username is registered in the platform, false otherwise
     */
    public boolean existsUserName(String username) {
        String query = "SELECT COUNT(*) > 0 FROM USERS WHERE username = ?";

        return jdbcTemplate.queryForObject(
            query, Boolean.class, username
        );
    }



    /**
     * Checks an emails' existence in the platform.
     * Should be Used Before Creating new User.
     * @param email the email to check
     * @return true if the email is registered in the platform, false otherwise
     */
    public boolean existsUserMail(String email) {
        String query = "SELECT COUNT(*) > 0 FROM USERS WHERE email = ?";

        return jdbcTemplate.queryForObject(
            query, Boolean.class, email
        );
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
                password
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

        jdbcTemplate.update(insert, username, email, password);
    }
}
