package globallogic.evaluate.msglsignup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Setter
@Getter
@AllArgsConstructor
public class SignInDto {
    @Email
    private String email;
    private String password;
}
