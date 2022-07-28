package globallogic.evaluate.msglsignup.dto;

import globallogic.evaluate.msglsignup.model.Phone;
import lombok.Data;

import javax.validation.constraints.Email;
import java.util.List;

@Data
public class CreateUserDto {
    private int id;
    private String name;
    @Email
    private String email;
    private String password;
    private List<Phone> phones;

}
