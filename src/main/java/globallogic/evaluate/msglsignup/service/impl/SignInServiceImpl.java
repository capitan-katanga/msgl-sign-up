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

@Log4j2
@Service
@AllArgsConstructor
public class SignInServiceImpl implements SignInService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public GetAccessTokenDto signIn(SignInDto signInDto) {
        log.info("Start sign-in process.");
        var user = userRepository.findByEmail(signInDto.getEmail())
                .orElseThrow(
                        () -> new UserNotFoundException("User email not found")
                );
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInDto.getEmail(), signInDto.getPassword())
        );
        log.info("Found user with email: {}", signInDto.getEmail());
        log.info("Authentication manager successful");
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        log.info("Last login updated.");
        return new GetAccessTokenDto(jwtProvider.createAccessToken(signInDto.getEmail()));
    }

    public GetUserDto getUserDetailById(UUID userId) {
        log.info("Start get user detail process.");
        var user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User id not found"));
        log.info("Found user detail by id: {}", user);
        return userMapper.toGetUserDto(user);
    }

}
