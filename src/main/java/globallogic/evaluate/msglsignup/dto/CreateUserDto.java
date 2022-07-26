package globallogic.evaluate.msglsignup.dto;

import globallogic.evaluate.msglsignup.model.Phone;
import lombok.Data;

import java.util.List;
import java.util.regex.Pattern;

@Data
public class CreateUserDto {
    private int id;
    private String name;
    private String email;
    private String password;
    private List<Phone> phones;

    public void setEmail(String email) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if (Pattern.compile(regexPattern).matcher(email).matches())
            this.email = email;
        else
            throw new RuntimeException("Wrong email address");
    }
}
