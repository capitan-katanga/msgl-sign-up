package globallogic.evaluate.msglsignup.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ErrorMessage {
    private List<ErrorDetail> error;
}
