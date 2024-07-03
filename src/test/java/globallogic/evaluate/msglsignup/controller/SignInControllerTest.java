package globallogic.evaluate.msglsignup.controller;

import globallogic.evaluate.msglsignup.dto.GetAccessTokenDto;
import globallogic.evaluate.msglsignup.dto.SignInDto;
import globallogic.evaluate.msglsignup.exception.UserNotFoundException;
import globallogic.evaluate.msglsignup.security.jwt.JwtProvider;
import globallogic.evaluate.msglsignup.service.SignInService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;

import static globallogic.evaluate.msglsignup.DataMock.getUserMock;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SignInControllerTest {

    @InjectMocks
    private SignInController signInController;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private SignInService signInService;

    @Test
    @DisplayName("Successful SignIn")
    void signInTest() {
        var accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkdW1teUBnbWFpbC5jb20iLCJpc3MiOiJnbG9iYWwgbG9naWMiLCJpYXQiOjE3MTc5NzU5MDksImV4cCI6MTcxNzk4MzEwOX0.y_TcHzcqRZPGs0Yz8mZzt8BQrYIYKUMs4O7I6bsMCOA";
        when(signInService.signIn(any(SignInDto.class)))
                .thenReturn(new GetAccessTokenDto(accessToken));
        var response = signInController.signIn(new SignInDto("", ""));
        assertAll(
                () -> assertThat(response.getStatusCode(), equalTo(HttpStatus.OK)),
                () -> assertThat(response.getBody(), equalTo(new GetAccessTokenDto(accessToken)))
        );
    }

    @Test
    @DisplayName("Login with email not registered")
    void userNotRegistered() {
        var signInDto = new SignInDto("dummy@gmail.com", "Password12");
        when(signInService.signIn(signInDto)).thenThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class,
                () -> signInController.signIn(signInDto));
    }

    @Test
    @DisplayName("Get existing user detail")
    void getUserDetailTest() {
        var getUserDto = getUserMock();
        when(signInService.getUserDetailById(any(Integer.class)))
                .thenReturn(getUserDto);
        var response = signInController.getUserDetail(1);
        assertAll(
                () -> assertThat(response.getStatusCode(), equalTo(HttpStatus.OK)),
                () -> assertThat(response.getBody(), equalTo(getUserDto))
        );
    }
}