package globallogic.evaluate.msglsignup.service;

import globallogic.evaluate.msglsignup.dto.GetAccessTokenDto;
import globallogic.evaluate.msglsignup.dto.GetUserDto;
import globallogic.evaluate.msglsignup.dto.SignInDto;

public interface SignInService {
    GetAccessTokenDto signIn(SignInDto signInDto);

    GetUserDto getUserDetailById(Integer userId);
}
