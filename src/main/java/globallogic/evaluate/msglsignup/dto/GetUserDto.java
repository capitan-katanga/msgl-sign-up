package globallogic.evaluate.msglsignup.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class GetUserDto {
    private String id;
    @JsonFormat(pattern = "MMM dd, yyyy hh:mm:ss a")
    private LocalDateTime created;
    @JsonFormat(pattern = "MMM dd, yyyy hh:mm:ss a")
    private LocalDateTime lastLogin;
    private boolean isActive;
    private String name;
    private String email;
    private String password;
    @JsonProperty("phones")
    private List<PhoneDto> phoneDtoList;

    @Data
    @Builder
    public static class PhoneDto {
        private long number;
        @JsonProperty("citycode")
        private int cityCode;
        @JsonProperty("countrycode")
        private String countryCode;
    }
}
