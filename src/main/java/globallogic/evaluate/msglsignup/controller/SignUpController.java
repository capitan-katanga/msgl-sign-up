package globallogic.evaluate.msglsignup.controller;

import globallogic.evaluate.msglsignup.model.User;
import globallogic.evaluate.msglsignup.service.SignUpService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1")
@RestController
public class SignUpController {

    private final SignUpService signUpService;

    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewUser(@RequestBody User user) {
        this.signUpService.saveNewUser(user);
    }

}
