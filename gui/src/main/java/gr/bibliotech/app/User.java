package gr.bibliotech.app;

/**
 * User Class.
 * Represents a User.
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
}
