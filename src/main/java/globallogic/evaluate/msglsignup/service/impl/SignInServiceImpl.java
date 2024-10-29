package globallogic.evaluate.msglsignup.service.impl;

import globallogic.evaluate.msglsignup.dto.GetAccessTokenDto;
import globallogic.evaluate.msglsignup.dto.GetUserDto;
import globallogic.evaluate.msglsignup.mapper.UserMapper;
import globallogic.evaluate.msglsignup.dto.SignInDto;
import globallogic.evaluate.msglsignup.exception.UserNotFoundException;
import globallogic.evaluate.msglsignup.repository.UserRepository;
import globallogic.evaluate.msglsignup.security.jwt.JwtProvider;
import globallogic.evaluate.msglsignup.service.SignInService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Service implementation for user sign-in and retrieving user details.
 *
 * <p>This class implements {@link SignInService} and provides the logic necessary for users
 * to sign in and retrieve their details from the database. It validates credentials using
 * the {@link AuthenticationManager} and generates JWT tokens with {@link JwtProvider}.</p>
 */
@Log4j2
@Service
@AllArgsConstructor
public class SignInServiceImpl implements SignInService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Performs the sign-in process and returns a JWT access token.
     *
     * <p>This method validates the provided credentials and, if successful, records the
     * user's last login time and generates a JWT access token.</p>
     *
     * <p>Process flow:</p>
     * <ul>
     *   <li>Searches for the user in the database by email.</li>
     *   <li>If the user is not found, it throws a {@link UserNotFoundException}.</li>
     *   <li>Validates the credentials using {@link AuthenticationManager}.</li>
     *   <li>Updates the user’s last login date.</li>
     *   <li>Generates and returns a JWT token using {@link JwtProvider}.</li>
     * </ul>
     *
     * @param signInDto DTO containing the user's credentials (email and password).
     * @return A {@link GetAccessTokenDto} object containing the generated JWT access token.
     * @throws UserNotFoundException If no user is found with the provided email.
     */
    public GetAccessTokenDto signIn(SignInDto signInDto) {
        log.info("Starting sign-in process.");
        var user = userRepository.findByEmail(signInDto.getEmail())
                .orElseThrow(
                        () -> new UserNotFoundException("User email not found")
                );
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInDto.getEmail(), signInDto.getPassword())
        );
        log.info("User found with email: {}", signInDto.getEmail());
        log.info("Authentication successful.");
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        log.info("Last login date updated.");

        return new GetAccessTokenDto(jwtProvider.createAccessToken(signInDto.getEmail()));
    }

    /**
     * Retrieves user details based on their ID.
     *
     * <p>This method searches for a user in the database by their ID and returns a DTO containing their details.</p>
     *
     * <p>Process flow:</p>
     * <ul>
     *   <li>Searches for the user in the database by ID.</li>
     *   <li>If the user is not found, it throws a {@link UserNotFoundException}.</li>
     *   <li>Returns a {@link GetUserDto} object with the found user's details.</li>
     * </ul>
     *
     * @param userId UUID of the user to be retrieved.
     * @return A {@link GetUserDto} object containing the user's details.
     * @throws UserNotFoundException If no user is found with the provided ID.
     */
    public GetUserDto getUserDetailById(UUID userId) {
        log.info("Starting user detail retrieval process.");
        var user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User ID not found"));
        log.info("User details found: {}", user);
        return userMapper.toGetUserDto(user);
    }
}
