package globallogic.evaluate.msglsignup.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthRequestBody {
    private String email;
    private String password;
}
