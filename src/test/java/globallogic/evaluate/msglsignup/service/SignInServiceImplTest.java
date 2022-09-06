package globallogic.evaluate.msglsignup.service;

import globallogic.evaluate.msglsignup.DataMock;
import globallogic.evaluate.msglsignup.exception.UserNotFoundException;
import globallogic.evaluate.msglsignup.model.User;
import globallogic.evaluate.msglsignup.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
class SignInServiceImplTest {

    @MockBean
    UserRepo userRepo;
    @Autowired
    SignInService signInService;

    @Test
    @DisplayName("First time update last login date")
    void updateLastLoginDateFirstLoginTest() {
        Mockito.when(userRepo.findByEmail("ignacioencizo@gmail.com")).thenReturn(Optional.ofNullable(DataMock.createUser01()));
        User user = userRepo.findByEmail("ignacioencizo@gmail.com").orElseThrow();
        Assertions.assertNull(user.getLastLogin());
        signInService.updateLastLoginDate("ignacioencizo@gmail.com");
        Assertions.assertNotNull(user.getLastLogin());
    }

    @Test
    @DisplayName("Modify last login date")
    void modifyLastLoginDateTest() {
        User user = DataMock.createUser01();
        LocalDateTime dateTimeNow = LocalDateTime.now();
        user.setLastLogin(dateTimeNow);
        Mockito.when(userRepo.findByEmail("ignacioencizo@gmail.com")).thenReturn(Optional.of(user));
        Assertions.assertEquals(user.getLastLogin(), dateTimeNow);
        signInService.updateLastLoginDate("ignacioencizo@gmail.com");
        Assertions.assertNotEquals(user.getLastLogin(), dateTimeNow);
    }

    @Test
    @DisplayName("User not registered in the app")
    void userNotFoundExceptionTest() {
        Assertions.assertThrows(UserNotFoundException.class, () -> signInService.updateLastLoginDate("robertototo@gmail.com"));
    }
}