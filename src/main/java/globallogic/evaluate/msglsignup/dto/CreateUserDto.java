package globallogic.evaluate.msglsignup.dto;

import globallogic.evaluate.msglsignup.model.Phone;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CreateUserDto {

    private final LocalDateTime created = LocalDateTime.now();
    private LocalDateTime lastLogin;
    private String token;
    private final boolean isActive = true;
    private String name;
    @Email
    private String email;
    private String password;
    private List<Phone> phones;

}
