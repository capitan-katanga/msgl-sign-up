package globallogic.evaluate.msglsignup.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhoneDto {

    private long number;
    @JsonProperty("citycode")
    private int cityCode;
    @JsonProperty("countrycode")
    private String countryCode;

}
