package globallogic.evaluate.msglsignup.service.impl;

import globallogic.evaluate.msglsignup.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

import static org.springframework.security.core.userdetails.User.withUsername;

/**
 * Service implementation for loading user data during authentication.
 *
 * <p>This class implements {@link UserDetailsService} to provide user details
 * based on their email. It interacts with the repository {@link UserRepository}
 * to retrieve user information and throws the exception {@link UsernameNotFoundException}
 * if the user is not found.</p>
 */
@Log4j2
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructor that initializes the user repository dependency.
     *
     * @param userRepository Repository used to retrieve user data from the database.
     */
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads the user details based on their email.
     *
     * <p>This method queries the repository {@link UserRepository} to find a user by their email.
     * If the user exists, it builds a {@link UserDetails} object with the retrieved data.
     * If the user is not found, it throws a {@link UsernameNotFoundException}.</p>
     *
     * <p>The returned {@code UserDetails} object contains the following properties:</p>
     * <ul>
     *   <li>Email as the username.</li>
     *   <li>Password from the user record.</li>
     *   <li>An empty set of roles.</li>
     *   <li>Account, credentials, and lock status set to active (not expired or locked).</li>
     * </ul>
     *
     * @param email The email of the user to retrieve.
     * @return A {@link UserDetails} object with the user’s information.
     * @throws UsernameNotFoundException If no user is found with the provided email.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Starting the process to retrieve user details.");
        var userApp = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("No user found with the email: " + email));
        log.info("User found in the database. \nUser:{}", userApp);
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
