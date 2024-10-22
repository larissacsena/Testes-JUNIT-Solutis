package com.mightyjava.test;

import com.mightyjava.controller.FooController;
import com.mightyjava.controller.UserController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {UserController.class, FooController.class}
)
@AutoConfigureMockMvc
public class WebApplicationSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails("teco")
    public void testManagerUserFooSaluteEndpointReturnsOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/foo/salute")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("teco")));
    }

    @Test
    @WithUserDetails("tal")
    public void testBasicUserCannotAccessUserAddEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/add")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
