package globallogic.evaluate.msglsignup.service

import globallogic.evaluate.msglsignup.DataMock
import globallogic.evaluate.msglsignup.repository.UserRepository
import globallogic.evaluate.msglsignup.service.impl.UserDetailsServiceImpl
import org.springframework.security.core.userdetails.UsernameNotFoundException
import spock.lang.Specification
import spock.lang.Subject

class UserDetailsServiceImplTest extends Specification {

    def userRepository = Mock(UserRepository)

    @Subject
    def userDetailsService = new UserDetailsServiceImpl(userRepository)

    def "Find user by existing email"() {
        given: "A user exists with the specified email"
        def user = DataMock.userEntityMock()

        when: "The repository returns the user by email"
        userRepository.findByEmail(user.email) >> Optional.of(user)
        def userDetail = userDetailsService.loadUserByUsername(user.email)

        then: "The user details are correctly loaded"
        userDetail != null
        userDetail.username == user.email
        userDetail.password == user.password
        userDetail.isAccountNonExpired()
        userDetail.isAccountNonLocked()
        userDetail.isCredentialsNonExpired()
        userDetail.isEnabled()
    }

    def "Find user by non-existing email"() {
        given: "No user exists with the specified email"
        def nonExistingEmail = "dummy@gmail.com"

        when: "The repository does not find any user by email"
        userRepository.findByEmail(nonExistingEmail) >> Optional.empty()
        userDetailsService.loadUserByUsername(nonExistingEmail)

        then: "A UsernameNotFoundException is thrown"
        thrown(UsernameNotFoundException)
    }
}
