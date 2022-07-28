package globallogic.evaluate.msglsignup.dto;

import globallogic.evaluate.msglsignup.model.User;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public User toUser(CreateUserDto createUserDto) {
        return User.builder()
                .id(createUserDto.getId())
                .name(createUserDto.getName())
                .email(createUserDto.getEmail())
                .password(createUserDto.getPassword())
                .email(createUserDto.getEmail())
                .phones(createUserDto.getPhones())
                .build();
    }
}
