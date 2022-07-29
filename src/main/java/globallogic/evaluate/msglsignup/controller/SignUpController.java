package globallogic.evaluate.msglsignup.controller;

import globallogic.evaluate.msglsignup.dto.CreateUserDto;
import globallogic.evaluate.msglsignup.dto.GetUserDto;
import globallogic.evaluate.msglsignup.dto.Mapper;
import globallogic.evaluate.msglsignup.model.User;
import globallogic.evaluate.msglsignup.service.SignUpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/api/v1")
@RestController
public class SignUpController {

    private final SignUpService signUpService;
    private final Mapper mapper;

    public SignUpController(SignUpService signUpService, Mapper mapper) {
        this.signUpService = signUpService;
        this.mapper = mapper;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<GetUserDto> createNewUser(@Valid @RequestBody CreateUserDto createUserDto) {
        User user = mapper.toUser(createUserDto);
        return new ResponseEntity<>(signUpService.saveNewUser(user), HttpStatus.CREATED);
    }

}
