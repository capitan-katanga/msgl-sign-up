package globallogic.evaluate.msglsignup.controller;

import globallogic.evaluate.msglsignup.dto.CreateUserDto;
import globallogic.evaluate.msglsignup.service.SignUpService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static globallogic.evaluate.msglsignup.DataMock.createUserMock;
import static globallogic.evaluate.msglsignup.DataMock.getUserMock;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SignUpControllerTest {

    @InjectMocks
    private SignUpController signUpController;

    @Mock
    private SignUpService signUpService;

    @Test
    @DisplayName("Save new user")
    void saveNewUserOkTest() {
        when(signUpService.saveNewUser(any(CreateUserDto.class)))
                .thenReturn(getUserMock());
        var response = signUpController.createNewUser(createUserMock());
        assertAll(
                () -> assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED)),
                () -> assertThat(response.getBody(), equalTo(getUserMock()))
        );
    }

}