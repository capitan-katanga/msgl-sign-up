package globallogic.evaluate.msglsignup.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage wrongEmailAddress(MethodArgumentNotValidException exception) {
        Date date = new Date();
        List<ErrorDetail> errorDetails = new ArrayList<>();

        errorDetails.add(ErrorDetail.builder()
                .timestamp(new Timestamp(date.getTime()))
                .codigo(HttpStatus.BAD_REQUEST.toString())
                .detail(Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage())
                .build());

        return ErrorMessage.builder()
                .error(errorDetails)
                .build();
    }

    @ExceptionHandler(MailAlreadyRegisteredException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage AlreadyRegisteredEmailAddress(MailAlreadyRegisteredException exception) {
        Date date = new Date();
        List<ErrorDetail> errorDetails = new ArrayList<>();

        errorDetails.add(ErrorDetail.builder()
                .timestamp(new Timestamp(date.getTime()))
                .codigo(HttpStatus.BAD_REQUEST.toString())
                .detail(exception.getMessage())
                .build());

        return ErrorMessage.builder()
                .error(errorDetails)
                .build();
    }

}
