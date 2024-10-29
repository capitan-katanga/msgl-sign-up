package globallogic.evaluate.msglsignup

import globallogic.evaluate.msglsignup.dto.CreateUserDto
import globallogic.evaluate.msglsignup.dto.GetUserDto
import globallogic.evaluate.msglsignup.dto.PhoneDto
import globallogic.evaluate.msglsignup.entity.PhoneEntity
import globallogic.evaluate.msglsignup.entity.UserEntity
import org.assertj.core.util.Lists

import java.time.LocalDateTime

class DataMock {

    private static final LocalDateTime dateTime = LocalDateTime.now()

    private DataMock() {
    }

    static UserEntity userEntityMock() {
        return UserEntity.builder()
                .id(UUID.randomUUID())
                .created(LocalDateTime.now())
                .lastLogin(null)
                .isActive(true)
                .name("dummy")
                .email("dummy@gmail.com")
                .password("Password12")
                .phoneEntities([phoneEntityMock()])
                .build()
    }

    static PhoneEntity phoneEntityMock() {
        return PhoneEntity.builder()
                .id(UUID.fromString("0e0ac888-c821-4e51-94b1-d9db4a136d14"))
                .number(161601L)
                .cityCode(3794)
                .countryCode("+54")
                .build()
    }

    static GetUserDto getUserDtoMock() {
        return GetUserDto.builder()
                .created(dateTime.minusHours(1))
                .lastLogin(dateTime)
                .isActive(true)
                .name("sandokan")
                .email("sandokan@gmail.com")
                .password("Password12")
                .phoneDtoList([PhoneDto.builder()
                                       .number(1111111111L)
                                       .cityCode(111)
                                       .countryCode("+54")
                                       .build()])
                .build()
    }

    static CreateUserDto createUserDtoMock() {
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
                .build()
    }

    static CreateUserDto mapperToCreateUserDto(UserEntity userEntity) {
        def phoneDtoList = userEntity.phoneEntities.collect { phoneEntity ->
            PhoneDto.builder()
                    .number(phoneEntity.number)
                    .cityCode(phoneEntity.cityCode)
                    .countryCode(phoneEntity.countryCode)
                    .build()
        }
        return CreateUserDto.builder()
                .name(userEntity.name)
                .email(userEntity.email)
                .password(userEntity.password)
                .phoneDtoList(phoneDtoList)
                .build()
    }
}
