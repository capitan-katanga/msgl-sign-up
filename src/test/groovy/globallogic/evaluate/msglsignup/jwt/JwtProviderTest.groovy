package globallogic.evaluate.msglsignup.jwt

import globallogic.evaluate.msglsignup.security.jwt.JwtProvider
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class JwtProviderTest extends Specification {

    String secretKey = "TExBVkVfTVVZX1NFQ1JFVEE"
    long validityInMilliseconds = 300000

    def jwtProvider = new JwtProvider(secretKey, validityInMilliseconds)

    def "Create access token JWT "() {
        given: "A valid email"
        def email = "dummy@gmail.com"

        when: "A new JWT access token is created"
        def jwtToken = jwtProvider.createAccessToken(email)

        then: "The token is generated correctly"
        print("token jwt -----------> \n" + jwtToken + "<------------ token jwt")
        jwtToken != null
        jwtToken != ""
        jwtToken.length() > 20
    }

    def "Check that a JWT token is valid"() {
        given: "A valid token JWT"
        def jwtToken = jwtProvider.createAccessToken("dummy@gmail.com")
        when: "JWT Token is validated"
        def valid = jwtProvider.isValidToken(jwtToken)
        then: "The token is valid"
        valid
    }

    def "check that the JWT token is expired"() {
        given: "An expired token"
        def expiredToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkdW1teUBnbWFpbC5jb20iLCJpc3MiOiJnbG9iYWwtbG9naWMiLCJpYXQiOjE3MzAxNjI5MjMsImV4cCI6MTczMDE2MzIyM30.jGCVDGn3s0ZlWr0hpu3VMESQSzS_-HzOUqXwHRGsi5g"
        when: "JWT Token is validated"
        def valid = jwtProvider.isValidToken(expiredToken)
        then: "The token is expired"
        !valid
    }

    def "Get subject from token"() {
        given: "A token with valid email"
        def validEmail = "dummy@gmail.com"
        def token = jwtProvider.createAccessToken(validEmail)
        when: "The email of the token is obtained"
        def email = jwtProvider.getSubject(token)
        then: "The email is correct"
        email == validEmail
    }

}
