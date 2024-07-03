package globallogic.evaluate.msglsignup.service;

import globallogic.evaluate.msglsignup.dto.GetAccessTokenDto;
import globallogic.evaluate.msglsignup.dto.GetUserDto;
import globallogic.evaluate.msglsignup.dto.Mapper;
import globallogic.evaluate.msglsignup.dto.SignInDto;
import globallogic.evaluate.msglsignup.exception.UserNotFoundException;
import globallogic.evaluate.msglsignup.repository.UserRepo;
import globallogic.evaluate.msglsignup.security.jwt.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Log4j2
@Service
@AllArgsConstructor
public class SignInServiceImpl implements SignInService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserRepo userRepository;
    private final Mapper mapper;

    public GetAccessTokenDto signIn(SignInDto signInDto) {
        var user = userRepository.findByEmail(signInDto.getEmail())
                .orElseThrow(
                        () -> new UserNotFoundException("User email not found"));

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInDto.getEmail(), signInDto.getPassword()));
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        log.info("Last login updated");
        return new GetAccessTokenDto(jwtProvider.createAccessToken(signInDto.getEmail()));
    }

    public GetUserDto getUserDetailById(Integer userId) {
        var user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User id not found"));
        log.info("Found user detail by id: {}", user);
        return mapper.toGetUserDto(user);
    }
}
