package globallogic.evaluate.msglsignup.service.impl;

import globallogic.evaluate.msglsignup.dto.CreateUserDto;
import globallogic.evaluate.msglsignup.dto.GetUserDto;
import globallogic.evaluate.msglsignup.mapper.UserMapper;
import globallogic.evaluate.msglsignup.exception.MailAlreadyRegisteredException;
import globallogic.evaluate.msglsignup.repository.UserRepository;
import globallogic.evaluate.msglsignup.service.SignUpService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Log4j2
@Service
@AllArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public GetUserDto saveNewUser(CreateUserDto userDto) {
        log.info("Start save user process.");
        userRepository.findByEmail(userDto.getEmail())
                .ifPresent(user1 -> {
                    throw new MailAlreadyRegisteredException("The email: " + userDto.getEmail() + " is already registered");
                });
        log.info("User not found in database: {}", userDto.getEmail());
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setCreated(LocalDateTime.now());
        userDto.setActive(true);
        var user = userMapper.toUser(userDto);
        userRepository.save(user);
        log.info("Registered user: {}", user);
        return userMapper.toGetUserDto(user);
    }

}
