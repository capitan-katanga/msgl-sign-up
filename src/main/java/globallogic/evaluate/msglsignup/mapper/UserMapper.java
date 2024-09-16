package globallogic.evaluate.msglsignup.mapper;

import globallogic.evaluate.msglsignup.dto.CreateUserDto;
import globallogic.evaluate.msglsignup.dto.GetUserDto;
import globallogic.evaluate.msglsignup.dto.PhoneDto;
import globallogic.evaluate.msglsignup.entity.PhoneEntity;
import globallogic.evaluate.msglsignup.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserEntity toUser(CreateUserDto createUserDto) {
        var phoneEntity = createUserDto.getPhoneDtoList() != null ?
                createUserDto.getPhoneDtoList().stream()
                        .map(phoneDto -> PhoneEntity.builder()
                                .number(phoneDto.getNumber())
                                .cityCode(phoneDto.getCityCode())
                                .countryCode(phoneDto.getCountryCode())
                                .build())
                        .collect(Collectors.toList()) :
                null;

        return UserEntity.builder()
                .created(createUserDto.getCreated())
                .isActive(createUserDto.isActive())
                .name(createUserDto.getName())
                .email(createUserDto.getEmail())
                .password(createUserDto.getPassword())
                .phoneEntities(phoneEntity)
                .build();
    }

    public GetUserDto toGetUserDto(UserEntity userEntity) {
        var phoneDtoList = userEntity.getPhoneEntities() != null ?
                userEntity.getPhoneEntities().stream()
                        .map(phoneEntity -> PhoneDto.builder()
                                .number(phoneEntity.getNumber())
                                .cityCode(phoneEntity.getCityCode())
                                .countryCode(phoneEntity.getCountryCode())
                                .build())
                        .collect(Collectors.toList()) :
                null;

        return GetUserDto.builder()
                .id(String.valueOf(userEntity.getId()))
                .created(userEntity.getCreated())
                .lastLogin(userEntity.getLastLogin())
                .isActive(userEntity.isActive())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .phoneDtoList(phoneDtoList)
                .build();
    }

}
