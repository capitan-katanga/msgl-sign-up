package globallogic.evaluate.msglsignup.repository;

import globallogic.evaluate.msglsignup.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
class UserRepoTest {

    @Autowired
    UserRepo userRepository;

    @Test
    @DisplayName("Find user by email address")
    void findByEmailAddressTest() {
        Optional<User> user = userRepository.findByEmail("pedro@gmail.com");
        Assertions.assertTrue(user.isPresent());
        Assertions.assertAll(
                () -> Assertions.assertTrue(user.get().isActive()),
                () -> Assertions.assertEquals("pedro@gmail.com", user.get().getEmail()),
                () -> Assertions.assertEquals("pedro", user.get().getName()),
                () -> Assertions.assertNotNull(user.get().getCreated()),
                () -> Assertions.assertNull(user.get().getLastLogin()),
                () -> Assertions.assertFalse(user.get().getPhones().isEmpty())
        );
    }

    @Test
    @DisplayName("Find user by email address not registered")
    void findByEmailAddressTestNotRegistered() {
        Optional<User> user = userRepository.findByEmail("lalala@gmail.com");
        Assertions.assertFalse(user.isPresent());
    }

}