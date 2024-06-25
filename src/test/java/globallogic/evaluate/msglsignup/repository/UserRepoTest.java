package globallogic.evaluate.msglsignup.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepoTest {

    @Autowired
    UserRepo userRepository;

    @Test
    @DisplayName("Find user by email address")
    void findByEmailAddressTest() {
        var user = userRepository.findByEmail("pedro@gmail.com");
        assertTrue(user.isPresent());
        assertAll(
                () -> assertTrue(user.get().isActive()),
                () -> Assertions.assertEquals("pedro@gmail.com", user.get().getEmail()),
                () -> Assertions.assertEquals("pedro", user.get().getName()),
                () -> Assertions.assertNotNull(user.get().getCreated()),
                () -> Assertions.assertNull(user.get().getLastLogin()),
                () -> assertFalse(user.get().getPhones().isEmpty())
        );
    }

    @Test
    @DisplayName("Find user by email address not registered")
    void findByEmailAddressTestNotRegistered() {
        var user = userRepository.findByEmail("lalala@gmail.com");
        assertFalse(user.isPresent());
    }

}