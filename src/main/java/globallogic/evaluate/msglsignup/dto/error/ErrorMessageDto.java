package globallogic.evaluate.msglsignup.dto.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ErrorMessageDto {
    private List<ErrorDetail> error;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class ErrorDetail {
        private Timestamp timestamp;
        private Integer codigo;
        private String detail;
    }
}
