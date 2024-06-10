package globallogic.evaluate.msglsignup.exception;

public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -8974914429785039585L;

    public UserNotFoundException(String message) {
        super(message);
    }
}
