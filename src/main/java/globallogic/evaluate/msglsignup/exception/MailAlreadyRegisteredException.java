package globallogic.evaluate.msglsignup.exception;

public class MailAlreadyRegisteredException extends RuntimeException {
    public MailAlreadyRegisteredException(String message) {
        super(message);
    }
}
