package au.edu.rmit.sept.webapp.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    void constructorTest() {
        User user1 = new User(1, "John", "Doe","password1234","0411111111","test@email.com", "None");
        assertEquals(1, user1.userID());
    }
}
