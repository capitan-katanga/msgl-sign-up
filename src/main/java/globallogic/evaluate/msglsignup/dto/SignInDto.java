package globallogic.evaluate.msglsignup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class SignInDto {
    @NotEmpty(message = "Email may not be empty")
    @Email(message = "Invalid email")
    private String email;
    @NotEmpty(message = "Password may not be empty")
    private String password;
}
