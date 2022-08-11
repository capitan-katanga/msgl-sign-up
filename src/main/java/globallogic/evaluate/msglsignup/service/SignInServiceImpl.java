package globallogic.evaluate.msglsignup.service;

import globallogic.evaluate.msglsignup.exception.UserNotFoundException;
import globallogic.evaluate.msglsignup.model.User;
import globallogic.evaluate.msglsignup.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class SignInServiceImpl implements SignInService {

    private final UserRepo userRepository;

    public void updateLastLoginDate(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new UserNotFoundException("User email not found");
        });
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
    }
}
