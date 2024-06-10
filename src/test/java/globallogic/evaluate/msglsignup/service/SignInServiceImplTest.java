package globallogic.evaluate.msglsignup.service;

import globallogic.evaluate.msglsignup.DataMock;
import globallogic.evaluate.msglsignup.dto.Mapper;
import globallogic.evaluate.msglsignup.exception.UserNotFoundException;
import globallogic.evaluate.msglsignup.model.User;
import globallogic.evaluate.msglsignup.repository.UserRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class SignInServiceImplTest {

    @MockBean
    UserRepo userRepo;
    @Autowired
    SignInService signInService;

    @Test
    @DisplayName("First time update last login date")
    void updateLastLoginDateFirstLoginTest() {
        when(userRepo.findByEmail("dummy@gmail.com")).thenReturn(Optional.ofNullable(DataMock.createUser01()));
        User user = userRepo.findByEmail("dummy@gmail.com").orElseThrow(RuntimeException::new);
        assertNull(user.getLastLogin());
        signInService.updateLastLoginDate("dummy@gmail.com");
        assertNotNull(user.getLastLogin());
    }

    @Test
    @DisplayName("Modify last login date")
    void modifyLastLoginDateTest() {
        User user = DataMock.createUser01();
        LocalDateTime dateTimeNow = LocalDateTime.now();
        user.setLastLogin(dateTimeNow);
        when(userRepo.findByEmail("dummy@gmail.com")).thenReturn(Optional.of(user));
        assertEquals(user.getLastLogin(), dateTimeNow);
        signInService.updateLastLoginDate("dummy@gmail.com");
        assertNotEquals(user.getLastLogin(), dateTimeNow);
    }

    @Test
    @DisplayName("User not registered in the app")
    void userNotFoundExceptionTest() {
        assertThrows(UserNotFoundException.class, () -> signInService.updateLastLoginDate("robertototo@gmail.com"));
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