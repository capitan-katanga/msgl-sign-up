package globallogic.evaluate.msglsignup.service;

import globallogic.evaluate.msglsignup.DataMock;
import globallogic.evaluate.msglsignup.repository.UserRepository;
import globallogic.evaluate.msglsignup.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserEntityDetailsServiceImplTest {

    @MockBean
    UserRepository userRepository;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Test
    @DisplayName("Find user by existing mail")
    void findUserByExistingEmailTest() {
        var user01 = DataMock.createUser01();
        when(userRepository.findByEmail("dummy@gmail.com"))
                .thenReturn(Optional.ofNullable(user01));

        var userDetail = userDetailsService.loadUserByUsername("dummy@gmail.com");

        assertNotNull(user01);
        assertAll(
                () -> assertEquals(user01.getEmail(), userDetail.getUsername()),
                () -> assertEquals(user01.getPassword(), userDetail.getPassword()),
                () -> assertTrue(userDetail.isAccountNonExpired()),
                () -> assertTrue(userDetail.isAccountNonLocked()),
                () -> assertTrue(userDetail.isCredentialsNonExpired()),
                () -> assertTrue(userDetail.isEnabled())
        );
    }

    @Test
    @DisplayName("Find user by not existing mail")
    void findUserByNotExistingMailTest() {
        when(userRepository.findByEmail("dummy@gmail.com"))
                .thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("dummy@gmail.com"));

    }
}