package globallogic.evaluate.msglsignup.exception;

import io.jsonwebtoken.JwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
@RestControllerAdvice
public class ControllerExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage argumentNotValid(MethodArgumentNotValidException exception) {
        log.error("Argument not valid exception", exception);
        return getErrorMessage(ErrorDetail.builder()
                .timestamp(getTimestamp())
                .codigo(HttpStatus.BAD_REQUEST.toString())
                .detail(Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage()).build());
    }

    @ExceptionHandler(MailAlreadyRegisteredException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage alreadyRegisteredEmailAddress(MailAlreadyRegisteredException exception) {
        log.error("Mail already registered exception", exception);
        return getErrorMessage(ErrorDetail.builder()
                .timestamp(getTimestamp())
                .codigo(HttpStatus.BAD_REQUEST.toString())
                .detail(exception.getMessage()).build());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage userNotFound(UserNotFoundException exception) {
        log.error("User not found exception ", exception);
        return getErrorMessage(ErrorDetail.builder()
                .timestamp(getTimestamp())
                .codigo(HttpStatus.NOT_FOUND.toString())
                .detail(exception.getMessage()).build());
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorMessage jwtExceptions(JwtException exception) {
        log.error("Jwt exception ", exception);
        return getErrorMessage(ErrorDetail.builder()
                .timestamp(getTimestamp())
                .codigo(HttpStatus.UNAUTHORIZED.toString())
                .detail(exception.getMessage()).build());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorMessage jwtExceptions(AccessDeniedException exception) {
        log.error("Access Denied ", exception);
        return getErrorMessage(ErrorDetail.builder()
                .timestamp(getTimestamp())
                .codigo(HttpStatus.UNAUTHORIZED.toString())
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
