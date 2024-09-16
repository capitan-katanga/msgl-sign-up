package globallogic.evaluate.msglsignup.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import globallogic.evaluate.msglsignup.validation.PasswordConstraint;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CreateUserDto {
    private LocalDateTime created;
    private LocalDateTime lastLogin;
    private boolean isActive;
    private String name;
    @NotEmpty(message = "Email may not be empty")
    @Email(message = "Invalid email")
    private String email;
    @NotEmpty(message = "Password may not be empty")
    @PasswordConstraint
    private String password;
    @JsonProperty("phones")
    private List<PhoneDto> phoneDtoList;
}
