package globallogic.evaluate.msglsignup.controller;

import globallogic.evaluate.msglsignup.dto.CreateUserDto;
import globallogic.evaluate.msglsignup.dto.GetUserDto;
import globallogic.evaluate.msglsignup.model.AuthRequestBody;
import globallogic.evaluate.msglsignup.service.JwtUtilService;
import globallogic.evaluate.msglsignup.service.SignUpService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/v1")
@RestController
@AllArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtilService jwtUtilService;

    @PostMapping("/sign-up")
    public ResponseEntity<GetUserDto> createNewUser(@Valid @RequestBody CreateUserDto userDto) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getEmail());
        userDto.setToken(jwtUtilService.generateToken(userDetails));
        return new ResponseEntity<>(signUpService.saveNewUser(userDto), HttpStatus.CREATED);
    }

    @PostMapping("/sing-in")
    public ResponseEntity<Map<String, String>> getAuthenticationToken(@RequestBody AuthRequestBody authRequestBody) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestBody.getEmail(),
                        authRequestBody.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequestBody.getEmail());
        Map<String, String> responseToke = new HashMap<>();
        responseToke.put("token", jwtUtilService.generateToken(userDetails));
        return new ResponseEntity<>(responseToke, HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<GetUserDto> getUserDetail(@PathVariable("email") String emailAddress) {
        return new ResponseEntity<>(signUpService.getUserDetail(emailAddress), HttpStatus.OK);
    }
}
