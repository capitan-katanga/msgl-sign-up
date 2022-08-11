package globallogic.evaluate.msglsignup.controller;

import globallogic.evaluate.msglsignup.dto.GetAccessTokenDto;
import globallogic.evaluate.msglsignup.dto.SignInDto;
import globallogic.evaluate.msglsignup.jwt.JwtUtil;
import globallogic.evaluate.msglsignup.service.SignInService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/api/v1")
@RestController
@AllArgsConstructor
public class SignInController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final SignInService signInService;


    @PostMapping("/sign-in")
    public ResponseEntity<GetAccessTokenDto> signIn(@RequestBody @Valid SignInDto signInDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInDto.getEmail(), signInDto.getPassword()
                ));
        String accessToken = jwtUtil.generateAccessToken(signInDto.getEmail());
        signInService.updateLastLoginDate(signInDto.getEmail());
        return new ResponseEntity<>(new GetAccessTokenDto(accessToken), HttpStatus.OK);
    }
}
