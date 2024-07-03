package globallogic.evaluate.msglsignup.service;

import globallogic.evaluate.msglsignup.DataMock;
import globallogic.evaluate.msglsignup.dto.Mapper;
import globallogic.evaluate.msglsignup.dto.SignInDto;
import globallogic.evaluate.msglsignup.exception.UserNotFoundException;
import globallogic.evaluate.msglsignup.repository.UserRepo;
import globallogic.evaluate.msglsignup.security.jwt.JwtProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class SignInServiceImplTest {

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    SignInService signInService;

    @Test
    @DisplayName("Login with email and password")
    void loginSuccessTest() {
        var user = DataMock.createUser01();
        var accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkdW1teUBnbWFpbC5jb20iLCJpc3MiOiJnbG9iYWwgbG9naWMiLCJpYXQiOjE3MTc5NzU5MDksImV4cCI6MTcxNzk4MzEwOX0.y_TcHzcqRZPGs0Yz8mZzt8BQrYIYKUMs4O7I6bsMCOA";
        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(jwtProvider.createAccessToken(user.getEmail())).thenReturn(accessToken);
        var token = signInService.signIn(new SignInDto(user.getEmail(), user.getPassword()));
        assertNotNull(token.getAccessToken());
    }

    @Test
    @DisplayName("Login with email not registered")
    void userNotFountTest() {
        assertThrows(UserNotFoundException.class,
                () -> signInService.signIn(new SignInDto("dummy@gmail.com", "Password12")));
    }

    @Test
    @DisplayName("Get existing user by id")
    void getExistingUserByIdTest() {
        var user = DataMock.createUser01();
        when(userRepo.findById(1))
                .thenReturn(Optional.ofNullable(user));
        var getUserDto = signInService.getUserDetailById(1);
        assertNotNull(user);
        assertEquals(getUserDto, new Mapper().toGetUserDto(user));
    }

    @Test
    @DisplayName("Get user not registered in the app")
    void getUserNotRegisteredTest() {
        assertThrows(UserNotFoundException.class, () -> signInService.getUserDetailById(1));
    }

}