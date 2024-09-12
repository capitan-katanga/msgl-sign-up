package globallogic.evaluate.msglsignup.service.impl;

import globallogic.evaluate.msglsignup.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

import static org.springframework.security.core.userdetails.User.withUsername;

@Log4j2
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Start process to get user details.");
        var userApp = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("No user found with the email: " + email));
        log.info("User found in data base. \nUser:{}", userApp);
        return withUsername(userApp.getEmail())
                .password(userApp.getPassword())
                .authorities(new HashSet<>())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

}
