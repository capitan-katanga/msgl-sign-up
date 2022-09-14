package globallogic.evaluate.msglsignup.service;

import globallogic.evaluate.msglsignup.DataMock;
import globallogic.evaluate.msglsignup.dto.CreateUserDto;
import globallogic.evaluate.msglsignup.dto.GetUserDto;
import globallogic.evaluate.msglsignup.exception.MailAlreadyRegisteredException;
import globallogic.evaluate.msglsignup.model.User;
import globallogic.evaluate.msglsignup.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
class SignUpServiceImplTest {

    @MockBean
    UserRepo userRepository;
    @Autowired
    SignUpService signUpService;

    CreateUserDto generateCreateUserDto(User user) {
        return CreateUserDto.builder().name(user.getName()).email(user.getEmail()).password(user.getPassword()).phones(user.getPhones()).build();
    }

    @Test
    @DisplayName("Save new user with all parameters")
    void saveNewUserAllParametersTest() {
        Mockito.when(userRepository.findByEmail("ignacioencizo@gmail.com")).thenReturn(Optional.empty());
        CreateUserDto createUserDto = generateCreateUserDto(DataMock.createUser01());
        GetUserDto getUserDto = signUpService.saveNewUser(createUserDto);
        Assertions.assertAll(
                () -> Assertions.assertNotNull(getUserDto.getCreated()),
                () -> Assertions.assertNull(getUserDto.getLastLogin()),
                () -> Assertions.assertTrue(getUserDto.isActive()),
                () -> Assertions.assertTrue(getUserDto.getPhones().size() > 0)
        );
    }

    @Test
    @DisplayName("Save new user with email already registered")
    void saveNewUserEmailAlreadyRegisteredExceptionTest() {
        Optional<User> userRegistered = Optional.of(DataMock.createUser01());
        Mockito.when(userRepository.findByEmail("ignacioencizo@gmail.com")).thenReturn(userRegistered);
        CreateUserDto createUserDto = generateCreateUserDto(DataMock.createUser01());
        Assertions.assertThrows(MailAlreadyRegisteredException.class, () -> signUpService.saveNewUser(createUserDto));
    }

    @Test
    @DisplayName("Save new user only with mandatory parameters")
    void saveNewUserMandatoryParametersTest() {
        Mockito.when(userRepository.findByEmail("ignacioencizo@gmail.com")).thenReturn(Optional.empty());
        CreateUserDto createUserDto = generateCreateUserDto(DataMock.createUser01());
        createUserDto.setCreated(null);
        createUserDto.setLastLogin(null);
        createUserDto.setName(null);
        createUserDto.setPhones(null);
        GetUserDto getUserDto = signUpService.saveNewUser(createUserDto);
        Assertions.assertAll(
                () -> Assertions.assertNotNull(createUserDto.getCreated()),
                () -> Assertions.assertNull(createUserDto.getLastLogin()),
                () -> Assertions.assertTrue(getUserDto.isActive()),
                () -> Assertions.assertNotNull(getUserDto.getEmail()),
                () -> Assertions.assertNotNull(getUserDto.getPassword())
        );
    }
}