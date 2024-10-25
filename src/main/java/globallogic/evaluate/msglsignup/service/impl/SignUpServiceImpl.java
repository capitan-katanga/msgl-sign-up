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

/**
 * Implementation of the service for registering new users.
 *
 * <p>This class implements {@link SignUpService} and provides the necessary logic to save
 * new users in the database. It checks whether the email is already registered,
 * encodes the user's password, and records the creation date.</p>
 */
@Log4j2
@Service
@AllArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Saves a new user in the database.
     *
     * <p>This method checks whether the provided email is already registered
     * in the database. If it is, it throws a {@link MailAlreadyRegisteredException}.
     * If the email is not registered, it encodes the user's password,
     * sets the creation date, marks the user as active, and finally saves the user
     * in the database.</p>
     *
     * @param userDto DTO containing the data of the user to be registered.
     * @return A {@link GetUserDto} object with the data of the registered user.
     * @throws MailAlreadyRegisteredException If the email is already registered.
     */
    public GetUserDto saveNewUser(CreateUserDto userDto) {
        log.info("Starting the process of saving a new user.");
        userRepository.findByEmail(userDto.getEmail())
                .ifPresent(user1 -> {
                    throw new MailAlreadyRegisteredException("The email: " + userDto.getEmail() + " is already registered");
                });
        log.info("User not found in the database: {}", userDto.getEmail());
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setCreated(LocalDateTime.now());
        userDto.setActive(true);
        var user = userMapper.toUser(userDto);
        userRepository.save(user);
        log.info("Registered user: {}", user);
        return userMapper.toGetUserDto(user);
    }
}
