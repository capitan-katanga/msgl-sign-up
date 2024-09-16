package globallogic.evaluate.msglsignup;

import globallogic.evaluate.msglsignup.dto.CreateUserDto;
import globallogic.evaluate.msglsignup.dto.GetUserDto;
import globallogic.evaluate.msglsignup.dto.PhoneDto;
import globallogic.evaluate.msglsignup.entity.PhoneEntity;
import globallogic.evaluate.msglsignup.entity.UserEntity;
import org.assertj.core.util.Lists;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

public class DataMock {

    private static final LocalDateTime dateTime = LocalDateTime.now();

    private DataMock() {
    }

    public static UserEntity createUser01() {
        return UserEntity.builder()
                .id(UUID.randomUUID())
                .created(LocalDateTime.now())
                .lastLogin(null)
                .isActive(true)
                .name("dummy")
                .email("dummy@gmail.com")
                .password("Password12")
                .phoneEntities(Collections.singletonList(createPhone01()))
                .build();
    }

    public static PhoneEntity createPhone01() {
        return PhoneEntity.builder()
                .id(UUID.fromString("0e0ac888-c821-4e51-94b1-d9db4a136d14"))
                .number(161601L)
                .cityCode(3794)
                .countryCode("+54").build();
    }

    public static GetUserDto getUserMock() {
        return GetUserDto.builder()
                .created(dateTime.minusHours(1))
                .lastLogin(dateTime)
                .isActive(true)
                .name("sandokan")
                .email("sandokan@gmail.com")
                .password("Password12")
                .phoneDtoList(Collections.singletonList(PhoneDto.builder()
                        .number(1111111111L)
                        .cityCode(111)
                        .countryCode("+54")
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
                .password("Password12")
                .phoneDtoList(Lists.list(PhoneDto.builder()
                        .number(1111111111L)
                        .cityCode(111)
                        .countryCode("+54")
                        .build()))
                .build();
    }

    public static CreateUserDto mapperToCreateUserDto(UserEntity userEntity) {
        var phoneDtoList = userEntity.getPhoneEntities().stream()
                .map(phoneEntity -> PhoneDto.builder()
                        .number(phoneEntity.getNumber())
                        .cityCode(phoneEntity.getCityCode())
                        .countryCode(phoneEntity.getCountryCode())
                        .build())
                .collect(Collectors.toList());
        return CreateUserDto.builder()
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .phoneDtoList(phoneDtoList).build();
    }

}
