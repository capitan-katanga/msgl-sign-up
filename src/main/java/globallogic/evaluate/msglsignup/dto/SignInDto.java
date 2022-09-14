package globallogic.evaluate.msglsignup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
public class SignInDto {
    @Email
    private String email;
    private String password;
}
