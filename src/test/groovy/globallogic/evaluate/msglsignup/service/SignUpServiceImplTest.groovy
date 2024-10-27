package globallogic.evaluate.msglsignup.service

import globallogic.evaluate.msglsignup.DataMock
import globallogic.evaluate.msglsignup.entity.UserEntity
import globallogic.evaluate.msglsignup.mapper.UserMapper
import globallogic.evaluate.msglsignup.repository.UserRepository
import globallogic.evaluate.msglsignup.service.impl.SignUpServiceImpl
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification
import spock.lang.Subject

class SignUpServiceImplTest extends Specification {

    def userRepository = Mock(UserRepository)
    def userMapper = Mock(UserMapper)
    def passwordEncoder = Mock(PasswordEncoder)

    @Subject
    def signUpService = new SignUpServiceImpl(userRepository, userMapper, passwordEncoder)

    def "Save new user with all parameters"() {
        given: "The user does not exist in the repository"
        def createUserDto = DataMock.createUserDtoMock()

        when: "Saving the new user"
        userRepository.findByEmail(createUserDto.email) >> Optional.empty()
        userMapper.toUser(createUserDto) >> DataMock.userEntityMock()
        userMapper.toGetUserDto(_ as UserEntity) >> DataMock.getUserDtoMock()
        def getUserDto = signUpService.saveNewUser(createUserDto)

        then: "The user is saved with the correct parameters"
        getUserDto == DataMock.getUserDtoMock()
    }

}

