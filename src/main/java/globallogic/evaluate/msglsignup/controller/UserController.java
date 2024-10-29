package globallogic.evaluate.msglsignup.controller;

import globallogic.evaluate.msglsignup.dto.CreateUserDto;
import globallogic.evaluate.msglsignup.dto.GetAccessTokenDto;
import globallogic.evaluate.msglsignup.dto.GetUserDto;
import globallogic.evaluate.msglsignup.dto.SignInDto;
import globallogic.evaluate.msglsignup.service.SignInService;
import globallogic.evaluate.msglsignup.service.SignUpService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final SignInService signInService;
    private final SignUpService signUpService;

    @PostMapping("/sign-in")
    public ResponseEntity<GetAccessTokenDto> signIn(@RequestBody @Valid SignInDto signInDto) {
        return new ResponseEntity<>(signInService.signIn(signInDto), HttpStatus.OK);
    }

    @GetMapping("/getUserDetail/{userId}")
    public ResponseEntity<GetUserDto> getUserDetail(@PathVariable("userId") UUID userId) {
        return new ResponseEntity<>(signInService.getUserDetailById(userId), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<GetUserDto> createNewUser(@Valid @RequestBody CreateUserDto userDto) {
        return new ResponseEntity<>(signUpService.saveNewUser(userDto), HttpStatus.CREATED);
    }

}
