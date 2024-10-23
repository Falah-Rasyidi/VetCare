package au.edu.rmit.sept.webapp.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.util.LocalStorage;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginPersistenceTests {

    @Autowired
    private MockMvc mockMvc;
    private MockHttpSession session;
    private User testUser;

    @BeforeEach
    public void setup() {
        session = new MockHttpSession();
        testUser = new User(1, "John", "Doe", "password123", "1234567890", "john.doe@example.com", "USER");
    }

    // Test that pages have proper access and contain correct user details in model
    @Test
    public void shouldAccessLoginInfo() throws Exception {
        session.setAttribute(LocalStorage.LOGGED_IN_USER_ID, testUser);

        mockMvc.perform(get("/").session(session))
                .andExpect(status().isOk())
                .andExpect(model().attribute("loggedInUser", testUser));
    }

    // Test that when not logged in, loggedInUser should be null
    @Test
    public void notLoggedInTest() throws Exception {
        session.invalidate(); // <-- make sure that user is logged out

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("loggedInUser", (Object) null));
    }
}