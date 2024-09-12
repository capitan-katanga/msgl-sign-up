package globallogic.evaluate.msglsignup.exception;

public class MailAlreadyRegisteredException extends RuntimeException {

    private static final long serialVersionUID = -4563215917086118459L;

    public MailAlreadyRegisteredException(String message) {
        super(message);
    }

}
