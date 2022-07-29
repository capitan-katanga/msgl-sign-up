package globallogic.evaluate.msglsignup.dto;

import globallogic.evaluate.msglsignup.model.User;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public User toUser(CreateUserDto createUserDto) {
        return User.builder()
                .created(createUserDto.getCreated())
                .isActive(createUserDto.isActive())
                .name(createUserDto.getName())
                .email(createUserDto.getEmail())
                .password(createUserDto.getPassword())
                .phones(createUserDto.getPhones())
                .build();
    }

    public GetUserDto toGetUserDto(User user) {
        return GetUserDto.builder()
                .id(user.getId())
                .created(user.getCreated())
                .isActive(user.isActive())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phones(user.getPhones())
                .build();
    }

}
