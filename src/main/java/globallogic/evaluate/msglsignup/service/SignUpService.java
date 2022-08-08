package globallogic.evaluate.msglsignup.service;

import globallogic.evaluate.msglsignup.dto.CreateUserDto;
import globallogic.evaluate.msglsignup.dto.GetUserDto;

public interface SignUpService {

    GetUserDto saveNewUser(CreateUserDto user);

    GetUserDto getUserDetail(String email);
}
