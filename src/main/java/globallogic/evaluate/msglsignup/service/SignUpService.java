package globallogic.evaluate.msglsignup.service;

import globallogic.evaluate.msglsignup.dto.GetUserDto;
import globallogic.evaluate.msglsignup.model.User;

public interface SignUpService {

    GetUserDto saveNewUser(User user);

}
