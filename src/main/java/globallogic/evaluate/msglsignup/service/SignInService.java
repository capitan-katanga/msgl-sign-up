package globallogic.evaluate.msglsignup.service;

import globallogic.evaluate.msglsignup.dto.GetUserDto;

public interface SignInService {
    void updateLastLoginDate(String email);

    GetUserDto getUserDetailById(Integer userId);
}
