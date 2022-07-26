package globallogic.evaluate.msglsignup.dto;

import globallogic.evaluate.msglsignup.model.User;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public User toUser(CreateUserDto createUserDto) {
        return new User(createUserDto.getId(), createUserDto.getName(), createUserDto.getEmail(), createUserDto.getPassword(), createUserDto.getPhones());
    }
}
