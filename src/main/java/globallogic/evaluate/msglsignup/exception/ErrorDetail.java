package globallogic.evaluate.msglsignup.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@Builder
public class ErrorDetail {
    private Timestamp timestamp;
    private String codigo;
    private String detail;
}
