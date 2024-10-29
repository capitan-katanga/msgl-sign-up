package globallogic.evaluate.msglsignup.repository


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
class UserEntityRepositoryTest extends Specification {

    @Autowired
    UserRepository userRepository

    def "Find user by email address"() {
        given: "An existing user with the email 'pedro@gmail.com'"
        def userEmail = "pedro@gmail.com"

        when: "We search for the user by email"
        def user = userRepository.findByEmail(userEmail)

        then: "The user is found and has the correct attributes"
        user.isPresent()
        with(user.get()) {
            isActive()
            email == userEmail
            name == "pedro"
            created != null
            lastLogin == null
            !phoneEntities.isEmpty()
        }
    }

    def "Find user by email address not registered"() {
        given: "A non-existent email 'lalala@gmail.com'"
        def nonExistentEmail = "lalala@gmail.com"

        when: "We search for the user by this email"
        def user = userRepository.findByEmail(nonExistentEmail)

        then: "No user is found"
        !user.isPresent()
    }
}
