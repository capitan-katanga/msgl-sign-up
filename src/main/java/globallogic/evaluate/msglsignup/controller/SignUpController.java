package globallogic.evaluate.msglsignup.controller;

import globallogic.evaluate.msglsignup.dto.CreateUserDto;
import globallogic.evaluate.msglsignup.dto.GetUserDto;
import globallogic.evaluate.msglsignup.service.SignUpService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/v1")
@RestController
@AllArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;

    @PostMapping("/sign-up")
    public ResponseEntity<GetUserDto> createNewUser(@Valid @RequestBody CreateUserDto userDto) {
        return new ResponseEntity<>(signUpService.saveNewUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping("/getUserDetail/{userId}")
    public ResponseEntity<GetUserDto> getUserDetail(@PathVariable("userId") Integer userId) {
        return new ResponseEntity<>(signUpService.getUserDetailById(userId), HttpStatus.OK);

    }

}
