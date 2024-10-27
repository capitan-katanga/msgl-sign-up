package globallogic.evaluate.msglsignup.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerMvcTest extends Specification {

    @Autowired
    MockMvc mockMvc

    def "Handler - Argument not valid exception"() {
        given: "A request with invalid password"
        def requestBody = '''{
            "name": "dummy",
            "email": "dummy@gmail.com",
            "password": "password12",
            "phones": [{
                "number": 4161601,
                "citycode": 379,
                "contrycode": "Argentina"
            }]
        }'''

        when: "The sign-up endpoint is invoked"
        def result = mockMvc.perform(post("/api/v1/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())

        then: "A BadRequest status is returned with the expected error details"
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath('$.error[0].timestamp').isNotEmpty())
                .andExpect(jsonPath('$.error[0].codigo').value("400"))
                .andExpect(jsonPath('$.error[0].detail').value("Invalid password"))
    }

    def "Handler - Mail already registered exception"() {
        given: "A request with registered email"
        def requestBody = """{
            "name": "dummy",
            "email": "pedro@gmail.com",
            "password": "Password12",
            "phones": [{
                "number": 4161601,
                "citycode": 379,
                "contrycode": "Argentina"
            }]
        }"""

        when: "The sign-up endpoint is invoked"
        def result = mockMvc.perform(post("/api/v1/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())

        then: "A BadRequest status is returned with the expected error details"
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath('$.error[0].timestamp').isNotEmpty())
                .andExpect(jsonPath('$.error[0].codigo').value("400"))
                .andExpect(jsonPath('$.error[0].detail').value("The email: pedro@gmail.com is already registered"))
    }

}
