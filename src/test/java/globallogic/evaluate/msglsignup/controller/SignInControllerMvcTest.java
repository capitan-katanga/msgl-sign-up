package globallogic.evaluate.msglsignup.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SignInControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Handler - Argument not valid exception")
    void argumentNotValidExceptionTest() throws Exception {
        mockMvc.perform(post("/api/v1/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"name\": \"dummy\",\n" +
                                "    \"email\": \"dummy@gmail.com\",\n" +
                                "    \"password\": \"password12\",\n" +
                                "    \"phones\": [\n" +
                                "        {\n" +
                                "            \"number\": 4161601,\n" +
                                "            \"citycode\": 379,\n" +
                                "            \"contrycode\": \"Argentina\"\n" +
                                "        }\n" +
                                "    ]\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error[0].timestamp").isNotEmpty())
                .andExpect(jsonPath("$.error[0].codigo").value("400"))
                .andExpect(jsonPath("$.error[0].detail").value("Invalid password"));
    }

}
