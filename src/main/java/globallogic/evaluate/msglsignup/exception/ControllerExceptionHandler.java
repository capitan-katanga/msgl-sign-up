package globallogic.evaluate.msglsignup.exception;

import globallogic.evaluate.msglsignup.dto.error.ErrorMessageDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Objects;

@Log4j2
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageDto argumentNotValid(MethodArgumentNotValidException exception) {
        log.error("Argument not valid exception", exception);
        return getErrorMessage(HttpStatus.BAD_REQUEST.value(), Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler(MailAlreadyRegisteredException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageDto alreadyRegisteredEmailAddress(MailAlreadyRegisteredException exception) {
        log.error("Mail already registered exception", exception);
        return getErrorMessage(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessageDto userNotFound(UserNotFoundException exception) {
        log.error("User not found exception ", exception);
        return getErrorMessage(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorMessageDto badCredentials(BadCredentialsException exception) {
        log.error("Bad credentials exception ", exception);
        return getErrorMessage(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
    }

    private ErrorMessageDto getErrorMessage(Integer code, String detail) {
        return ErrorMessageDto.builder()
                .error(Collections.singletonList(ErrorMessageDto.ErrorDetail.builder()
                        .timestamp(Timestamp.from(ZonedDateTime.now().toInstant()))
                        .codigo(code)
                        .detail(detail)
                        .build()))
                .build();
    }

}
