package globallogic.evaluate.msglsignup.controller;

import globallogic.evaluate.msglsignup.jwt.JwtFilter;
import globallogic.evaluate.msglsignup.jwt.JwtUtil;
import globallogic.evaluate.msglsignup.repository.UserRepo;
import globallogic.evaluate.msglsignup.service.SignUpService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SignUpController.class)
@AutoConfigureMockMvc(addFilters = false)
class SignUpControllerTest {

    @MockBean
    private UserRepo userRepository;
    @MockBean
    private SignUpService signUpService;
    @MockBean
    private JwtFilter jwtFilter;
    @MockBean
    private JwtUtil jwtUtil;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("Get user detail")
    void getUserDetailTest() throws Exception {
        //Todo: Mock SignUpServices method getUserDetailById
        mockMvc.perform(get("/api/v1/getUserDetail/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.name").value("ignacio"));
    }
}