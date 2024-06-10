package globallogic.evaluate.msglsignup.service;

import globallogic.evaluate.msglsignup.model.User;
import globallogic.evaluate.msglsignup.repository.UserRepo;
import globallogic.evaluate.msglsignup.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userApp = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No user found with the email: " + email));
        return new CustomUserDetails(userApp);
    }

}
