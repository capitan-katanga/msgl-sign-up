package globallogic.evaluate.msglsignup.controller;

import globallogic.evaluate.msglsignup.dto.CreateUserDto;
import globallogic.evaluate.msglsignup.dto.Mapper;
import globallogic.evaluate.msglsignup.model.User;
import globallogic.evaluate.msglsignup.service.SignUpService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewUser(@Valid @RequestBody CreateUserDto createUserDto) {
        User user = mapper.toUser(createUserDto);
        this.signUpService.saveNewUser(user);
    }

}
