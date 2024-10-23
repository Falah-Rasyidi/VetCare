package au.edu.rmit.sept.webapp.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SignUpTests {

    private static final String PAGE_URL = "/signup";

    @Autowired
    private MockMvc mockMvc;

    // Test that we are on the sign-up page
    @Test
    public void shouldDisplayTitle() throws Exception {
        mockMvc.perform(get(PAGE_URL)).andExpect(status().isOk()).andExpect(content().string(containsString("Sign up")));
    }

    // Test that all form fields are displaying
    @Test
    public void shouldDisplaySignUpForm() throws Exception {
        mockMvc.perform(get(PAGE_URL)).andExpect(status().isOk()).andExpect(content().string(containsString("First name")));
        mockMvc.perform(get(PAGE_URL)).andExpect(status().isOk()).andExpect(content().string(containsString("Last name")));
        mockMvc.perform(get(PAGE_URL)).andExpect(status().isOk()).andExpect(content().string(containsString("Email")));
        mockMvc.perform(get(PAGE_URL)).andExpect(status().isOk()).andExpect(content().string(containsString("Password")));
        mockMvc.perform(get(PAGE_URL)).andExpect(status().isOk()).andExpect(content().string(containsString("Confirm password")));
        mockMvc.perform(get(PAGE_URL)).andExpect(status().isOk()).andExpect(content().string(containsString("Phone number")));
    }
}
