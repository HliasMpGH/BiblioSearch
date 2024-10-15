package gr.bibliotech.data;

import java.util.regex.Pattern;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;

import gr.bibliotech.error.InvalidInfoException;
import gr.bibliotech.app.User;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

        if (userRepository.existsUserName(username)) {
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
        if (userRepository.existsUserMail(email)) {
            throw new InvalidInfoException("The email You Provided Already Exists! " +
                "Please Choose a Different One!", "Email");
        }
    }

    /**
     * @see gr.bibliotech.data.UserRepository#authenticate(String, String)
     */
    public User authenticate(String username, String password) {
        return userRepository.authenticate(username, getHash(password));
    }

    /**
     * @see gr.bibliotech.data.UserRepository#registerUser(String, String, String)
     */
    public void registerUser(String username, String email, String password) {
        userRepository.registerUser(username, email, getHash(password));
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
