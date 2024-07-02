package globallogic.evaluate.msglsignup.controller;

import globallogic.evaluate.msglsignup.dto.GetAccessTokenDto;
import globallogic.evaluate.msglsignup.dto.GetUserDto;
import globallogic.evaluate.msglsignup.dto.SignInDto;
import globallogic.evaluate.msglsignup.service.SignInService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/v1")
@RestController
@AllArgsConstructor
public class SignInController {

    private final SignInService signInService;

    @PostMapping("/sign-in")
    public ResponseEntity<GetAccessTokenDto> signIn(@RequestBody @Valid SignInDto signInDto) {
        return new ResponseEntity<>(signInService.signIn(signInDto), HttpStatus.OK);
    }

    @GetMapping("/getUserDetail/{userId}")
    public ResponseEntity<GetUserDto> getUserDetail(@PathVariable("userId") Integer userId) {
        return new ResponseEntity<>(signInService.getUserDetailById(userId), HttpStatus.OK);
    }

}
