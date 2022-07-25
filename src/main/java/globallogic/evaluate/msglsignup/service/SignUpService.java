package globallogic.evaluate.msglsignup.service;

import globallogic.evaluate.msglsignup.web.model.User;
import globallogic.evaluate.msglsignup.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {

    @Autowired
    UserRepo userRepository;

    public void saveNewUser(User user) {
        userRepository.save(user);
    }

}
