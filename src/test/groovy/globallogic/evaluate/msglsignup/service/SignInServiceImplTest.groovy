package globallogic.evaluate.msglsignup.service

import globallogic.evaluate.msglsignup.DataMock
import globallogic.evaluate.msglsignup.dto.SignInDto
import globallogic.evaluate.msglsignup.exception.UserNotFoundException
import globallogic.evaluate.msglsignup.mapper.UserMapper
import globallogic.evaluate.msglsignup.repository.UserRepository
import globallogic.evaluate.msglsignup.security.jwt.JwtProvider
import globallogic.evaluate.msglsignup.service.impl.SignInServiceImpl
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.AuthenticationManager
import spock.lang.Specification
import spock.lang.Subject

@SpringBootTest
class SignInServiceImplTest extends Specification {

    def authenticationManager = Mock(AuthenticationManager)
    def jwtProvider = Mock(JwtProvider)
    def userRepository = Mock(UserRepository)
    def userMapper = Mock(UserMapper)

    @Subject
    def signInService = new SignInServiceImpl(authenticationManager, jwtProvider, userRepository, userMapper)

    def "Login with email and password"() {
        given: "A user and a valid access token"
        def user = DataMock.userEntityMock()
        def accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkdW1teUBnbWFpbC5jb20iLCJpc3MiOiJnbG9iYWwgbG9naWMiLCJpYXQiOjE3MTc5NzU5MDksImV4cCI6MTcxNzk4MzEwOX0.y_TcHzcqRZPGs0Yz8mZzt8BQrYIYKUMs4O7I6bsMCOA"

        when: "The user is found in the repository and a token is generated"
        userRepository.findByEmail(user.email) >> Optional.of(user)
        jwtProvider.createAccessToken(user.email) >> accessToken
        def token = signInService.signIn(new SignInDto(user.email, user.password))

        then: "A valid access token is returned"
        token.accessToken == accessToken
    }

    def "Login with email not registered"() {
        given: "A non-registered user email"
        def signInDto = new SignInDto("dummy@gmail.com", "Password12")

        when: "The sign-in service is called"
        userRepository.findByEmail(signInDto.email) >> Optional.empty()
        signInService.signIn(signInDto)

        then: "UserNotFoundException is thrown"
        thrown(UserNotFoundException)
    }

    def "Get existing user by ID"() {
        given: "An existing user"
        def user = DataMock.userEntityMock()

        when: "The user is found by ID"
        userRepository.findById(user.id) >> Optional.of(user)
        userMapper.toGetUserDto(user) >> DataMock.getUserDtoMock()
        def getUserDto = signInService.getUserDetailById(user.id)

        then: "The returned DTO matches the mapped user data"
        getUserDto == DataMock.getUserDtoMock()
    }

    def "Get user not registered in the app"() {
        given: "A random UUID that does not correspond to any user"
        def randomUUID = UUID.randomUUID()

        when: "The service attempts to retrieve user details by ID"
        userRepository.findById(randomUUID) >> Optional.empty()
        signInService.getUserDetailById(randomUUID)

        then: "UserNotFoundException is thrown"
        thrown(UserNotFoundException)
    }
}
