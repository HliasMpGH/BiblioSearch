package gr.bibliotech.error;

public class InvalidInfoException extends RuntimeException {

    private String invalidCredential;

    public InvalidInfoException(String message) {
        super(message);
    }

    public InvalidInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidInfoException(Throwable cause) {
        super(cause);
    }

    public InvalidInfoException(String message, String invalidCredential) {
        super(message);
        this.invalidCredential = invalidCredential;
    }

    public String getInvalidCredential() {
        return invalidCredential;
    }

    public String getMessage() {
        return getInvalidCredential() + " Error: " + super.getMessage();
    }
}
