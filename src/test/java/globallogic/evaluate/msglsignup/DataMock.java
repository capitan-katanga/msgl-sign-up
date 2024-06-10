package globallogic.evaluate.msglsignup;

import globallogic.evaluate.msglsignup.dto.CreateUserDto;
import globallogic.evaluate.msglsignup.dto.GetUserDto;
import globallogic.evaluate.msglsignup.model.Phone;
import globallogic.evaluate.msglsignup.model.User;
import org.assertj.core.util.Lists;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class DataMock {

    private static final LocalDateTime dateTime = LocalDateTime.now();

    private DataMock() {
    }

    public static User createUser01() {
        return User.builder().id(1).created(LocalDateTime.now()).lastLogin(null).isActive(true)
                .name("dummy").email("dummy@gmail.com").password("Dummy123")
                .phones(new ArrayList<>(Collections.singletonList(createPhone01()))).build();
    }

    public static Phone createPhone01() {
        return Phone.builder().id(1).number(3512600000L).citycode(5000).countrycode("+54").build();
    }

    public static GetUserDto getUserMock() {
        return GetUserDto.builder()
                .created(dateTime.minusHours(1))
                .lastLogin(dateTime)
                .isActive(true)
                .name("sandokan")
                .email("sandokan@gmail.com")
                .password("lalala")
                .phones(Lists.list(Phone.builder()
                        .id(1)
                        .number(1111111111L)
                        .citycode(111)
                        .countrycode("+54")
                        .build()))
                .build();
    }

    public static CreateUserDto createUserMock() {
        return CreateUserDto.builder()
                .created(dateTime.minusHours(1))
                .lastLogin(dateTime)
                .isActive(true)
                .name("sandokan")
                .email("sandokan@gmail.com")
                .password("lalala")
                .phones(Lists.list(Phone.builder()
                        .id(1)
                        .number(1111111111L)
                        .citycode(111)
                        .countrycode("+54")
                        .build()))
                .build();
    }
}
