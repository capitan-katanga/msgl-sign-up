package globallogic.evaluate.msglsignup.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class ControllerExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage argumentNotValid(MethodArgumentNotValidException exception) {
        return getErrorMessage(ErrorDetail.builder()
                .timestamp(getTimestamp())
                .codigo(HttpStatus.BAD_REQUEST.toString())
                .detail(Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage()).build());
    }

    @ExceptionHandler(MailAlreadyRegisteredException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage alreadyRegisteredEmailAddress(MailAlreadyRegisteredException exception) {
        return getErrorMessage(ErrorDetail.builder()
                .timestamp(getTimestamp())
                .codigo(HttpStatus.BAD_REQUEST.toString())
                .detail(exception.getMessage()).build());
    }

    public ErrorMessage getErrorMessage(ErrorDetail errorDetail) {
        List<ErrorDetail> errorDetails = new ArrayList<>();
        errorDetails.add(errorDetail);
        return new ErrorMessage(errorDetails);
    }

    public Timestamp getTimestamp() {
        return Timestamp.from(ZonedDateTime.now().toInstant());
    }
}
