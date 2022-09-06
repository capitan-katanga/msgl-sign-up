package globallogic.evaluate.msglsignup.service;

import globallogic.evaluate.msglsignup.DataMock;
import globallogic.evaluate.msglsignup.dto.CreateUserDto;
import globallogic.evaluate.msglsignup.dto.GetUserDto;
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
        //Mockito.when(userRepository.save(DataMock.createUser01())).thenReturn();

        CreateUserDto createUserDto = generateCreateUserDto(DataMock.createUser01());
        GetUserDto getUserDto = signUpService.saveNewUser(createUserDto);
        Assertions.assertAll(
                () -> Assertions.assertNotNull(getUserDto.getCreated()),
                () -> Assertions.assertNull(getUserDto.getLastLogin()),
                () -> Assertions.assertTrue(getUserDto.isActive())
        );

    }

}