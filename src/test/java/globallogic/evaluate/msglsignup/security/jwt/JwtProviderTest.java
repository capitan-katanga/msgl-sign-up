package globallogic.evaluate.msglsignup.security.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtProviderTest {

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    @DisplayName("Generate new token")
    void createAccessToken() {
        var token = jwtProvider.createAccessToken("dummy@gmail.com");
        assertNotNull(token);
    }

    @Test
    @DisplayName("Validate correct token")
    void isValidToken() {
        var token = jwtProvider.createAccessToken("dummy@gmail.com");
        assertTrue(jwtProvider.isValidToken(token));
    }

    @Test
    @DisplayName("Validate malformed jwt token")
    void isNotValidToken() {
        var token = "not valid token";
        assertFalse(jwtProvider.isValidToken(token));
    }

    @Test
    @DisplayName("Validate expired token")
    void expiredJwtToken() {
        var expiredToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkdW1teUBnbWFpbC5jb20iLCJpc3MiOiJnbG9iYWwtbG9naWMiLCJpYXQiOjE3MTk5NTAzOTQsImV4cCI6MTcxOTk1MDY5NH0.JMhdccDoUI_TZFywbrE_yey8q4ikDzGUmIT_NqKApKU";
        assertFalse(jwtProvider.isValidToken(expiredToken));
    }

    @Test
    void getSubject() {
        var email = "dummy@gmail.com";
        var token = jwtProvider.createAccessToken(email);
        var subject = jwtProvider.getSubject(token);
        assertEquals(email, subject);
    }

}