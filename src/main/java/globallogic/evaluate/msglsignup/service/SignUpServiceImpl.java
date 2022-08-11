package globallogic.evaluate.msglsignup.service;

import globallogic.evaluate.msglsignup.dto.CreateUserDto;
import globallogic.evaluate.msglsignup.dto.GetUserDto;
import globallogic.evaluate.msglsignup.dto.Mapper;
import globallogic.evaluate.msglsignup.exception.MailAlreadyRegisteredException;
import globallogic.evaluate.msglsignup.exception.UserNotFoundException;
import globallogic.evaluate.msglsignup.model.User;
import globallogic.evaluate.msglsignup.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final UserRepo userRepository;
    private final Mapper mapper;
    private final PasswordEncoder passwordEncoder;

    public GetUserDto saveNewUser(CreateUserDto userDto) {
        Optional<User> userOptional = userRepository.findByEmail(userDto.getEmail());
        userOptional.ifPresent(user1 -> {
            throw new MailAlreadyRegisteredException("The email: " + userDto.getEmail() + " is already registered");
        });
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setCreated(LocalDateTime.now());
        userDto.setActive(true);
        User user = mapper.toUser(userDto);
        userRepository.save(user);
        return mapper.toGetUserDto(user);
    }

    public GetUserDto getUserDetailById(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException("User id not found");
        });
        return mapper.toGetUserDto(user);
    }

}
