package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    UserService userService;
    @Test
    void getUsers() {
        Collection<User> users = userService.getUsers();
        assertNotNull(users);
    }

    @Test
    void addUser() {
    }

    @Test
    void findUser() {
        Optional<User> res = userService.findUser("johndoe@test.com");
        assertNotNull(res);
    }

    @Test
    void verifyUser() {
    }
}