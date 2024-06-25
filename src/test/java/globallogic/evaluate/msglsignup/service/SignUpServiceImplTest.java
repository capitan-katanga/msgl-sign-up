package globallogic.evaluate.msglsignup.service;

import globallogic.evaluate.msglsignup.DataMock;
import globallogic.evaluate.msglsignup.exception.MailAlreadyRegisteredException;
import globallogic.evaluate.msglsignup.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class SignUpServiceImplTest {

    @MockBean
    UserRepo userRepository;
    @Autowired
    SignUpService signUpService;

    @Test
    @DisplayName("Save new user with all parameters")
    void saveNewUserAllParametersTest() {
        when(userRepository.findByEmail("dummy@gmail.com")).thenReturn(Optional.empty());
        var createUserDto = DataMock.mapperToCreateUserDto(DataMock.createUser01());
        var getUserDto = signUpService.saveNewUser(createUserDto);
        assertAll(
                () -> Assertions.assertNotNull(getUserDto.getCreated()),
                () -> Assertions.assertNull(getUserDto.getLastLogin()),
                () -> Assertions.assertTrue(getUserDto.isActive()),
                () -> Assertions.assertFalse(getUserDto.getPhones().isEmpty())
        );
    }

    @Test
    @DisplayName("Save new user with email already registered")
    void saveNewUserEmailAlreadyRegisteredExceptionTest() {
        var userRegistered = Optional.of(DataMock.createUser01());
        when(userRepository.findByEmail("dummy@gmail.com"))
                .thenReturn(userRegistered);
        var createUserDto = DataMock.mapperToCreateUserDto(DataMock.createUser01());
        assertThrows(MailAlreadyRegisteredException.class, () -> signUpService.saveNewUser(createUserDto));
    }

    @Test
    @DisplayName("Save new user only with mandatory parameters")
    void saveNewUserMandatoryParametersTest() {
        when(userRepository.findByEmail("dummy@gmail.com")).thenReturn(Optional.empty());
        var createUserDto = DataMock.mapperToCreateUserDto(DataMock.createUser01());
        createUserDto.setCreated(null);
        createUserDto.setLastLogin(null);
        createUserDto.setName(null);
        createUserDto.setPhones(null);
        var getUserDto = signUpService.saveNewUser(createUserDto);
        assertAll(
                () -> Assertions.assertNotNull(createUserDto.getCreated()),
                () -> Assertions.assertNull(createUserDto.getLastLogin()),
                () -> Assertions.assertTrue(getUserDto.isActive()),
                () -> Assertions.assertNotNull(getUserDto.getEmail()),
                () -> Assertions.assertNotNull(getUserDto.getPassword())
        );
    }

}