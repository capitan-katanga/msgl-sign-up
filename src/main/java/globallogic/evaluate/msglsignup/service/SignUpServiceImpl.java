package globallogic.evaluate.msglsignup.service;

import globallogic.evaluate.msglsignup.dto.CreateUserDto;
import globallogic.evaluate.msglsignup.dto.GetUserDto;
import globallogic.evaluate.msglsignup.dto.Mapper;
import globallogic.evaluate.msglsignup.exception.MailAlreadyRegisteredException;
import globallogic.evaluate.msglsignup.model.User;
import globallogic.evaluate.msglsignup.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final UserRepo userRepository;
    private final Mapper mapper;
    private final JwtUtilService jwtUtilService;

    public GetUserDto saveNewUser(CreateUserDto userDto) {
        Optional<User> userOptional = userRepository.findByEmail(userDto.getEmail());
        userOptional.ifPresent(user1 -> {
            throw new MailAlreadyRegisteredException("The email: " + userDto.getEmail() + " is already registered");
        });
        userDto.setToken(jwtUtilService.generateToken(userDto));
        User user = mapper.toUser(userDto);
        userRepository.save(user);
        return mapper.toGetUserDto(user);
    }

}
