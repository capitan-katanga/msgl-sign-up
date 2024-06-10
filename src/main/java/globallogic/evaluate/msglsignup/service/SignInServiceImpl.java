package globallogic.evaluate.msglsignup.service;

import globallogic.evaluate.msglsignup.dto.GetUserDto;
import globallogic.evaluate.msglsignup.dto.Mapper;
import globallogic.evaluate.msglsignup.exception.UserNotFoundException;
import globallogic.evaluate.msglsignup.model.User;
import globallogic.evaluate.msglsignup.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Log4j2
@Service
@AllArgsConstructor
public class SignInServiceImpl implements SignInService {

    private final UserRepo userRepository;
    private final Mapper mapper;

    public void updateLastLoginDate(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User email not found"));
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        log.info("Last login updated");
    }

    public GetUserDto getUserDetailById(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User id not found"));
        log.info("Found user detail by id: {}", user);
        return mapper.toGetUserDto(user);
    }
}
