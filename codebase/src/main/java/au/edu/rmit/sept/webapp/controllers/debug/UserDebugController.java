package au.edu.rmit.sept.webapp.controllers.debug;

import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.models.UserClass;
import au.edu.rmit.sept.webapp.services.UserService;
import au.edu.rmit.sept.webapp.util.LocalStorage;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Collection;

@Controller
public class UserDebugController {
    private UserService service;

    // Automatically connects Controller to User Service and constructs it
    @Autowired
    public UserDebugController(UserService service) {
        this.service = service;
    }

    // GET Mapping for general access of URL
    @GetMapping("/debug-user")
    public String debugPage(Model model, HttpSession session) {
//        incrementCount(session, HOME_VIEW_COUNT);
        System.out.println(session.getAttribute(LocalStorage.LOGGED_IN_USER_ID));
        Collection<User> users = service.getUsers(); // Get all users from DB
        model.addAttribute("users", users);
        model.addAttribute("newuser", new UserClass()); // Construct null user to insert details into
        return "debug-pages/user-debug";
    }

    // POST Mapping for POST request from form submission
    @PostMapping("/debug-user")
    public String debugPageFormSubmit(@ModelAttribute("user") UserClass newuser, Model model) {
        // NOTE RIGHT NOW THIS THING PRETTY MUCH REDIRECTS TO THE ORIGINAL PAGE WITH NEW USERS
        // IT SHOULD GO TO FURTHER PAGES
        service.addUser(newuser);
        Collection<User> users = service.getUsers();
        model.addAttribute("users", users);
        model.addAttribute("newuser", new UserClass());
        return "debug-pages/user-debug";
    }
    private void incrementCount(HttpSession session, String attr){
        var homeViewCount = session.getAttribute(attr) == null ? 0 : (Integer)session.getAttribute(attr);
        session.setAttribute(attr, ++homeViewCount);
    }
}
