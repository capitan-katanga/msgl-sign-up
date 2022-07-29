package globallogic.evaluate.msglsignup.service;

import globallogic.evaluate.msglsignup.dto.GetUserDto;
import globallogic.evaluate.msglsignup.dto.Mapper;
import globallogic.evaluate.msglsignup.exception.MailAlreadyRegisteredException;
import globallogic.evaluate.msglsignup.model.User;
import globallogic.evaluate.msglsignup.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    UserRepo userRepository;
    @Autowired
    Mapper mapper;

    public GetUserDto saveNewUser(User user) {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        userOptional.ifPresent(user1 -> {
            throw new MailAlreadyRegisteredException("The email: " + user.getEmail() + " is already registered");
        });
        userRepository.save(user);
        return mapper.toGetUserDto(user);
    }

}
