package au.edu.rmit.sept.webapp.controllers.debug;

import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


/**
 * Practice REST controller for API use <br/>
 * Most likely deprecated until further notice
 */
@RestController
public class UserRestController {
    private UserService service;

    @Autowired
    public UserRestController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public Collection<User> all() {
        return service.getUsers();
    }
}
