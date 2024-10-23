package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.UserClass;
import au.edu.rmit.sept.webapp.services.UserService;
import au.edu.rmit.sept.webapp.util.LocalStorage;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class LoginController {
    private final UserService service;
//    private final String LOGGED_IN_USER_ID = "LOGGED_IN_USER_ID";

    @Autowired
    LoginController(UserService service) {
        this.service = service;
    }

    @GetMapping("/login_user")
    public String login(Model model) {
        model.addAttribute("user", new UserClass());

        // Error ArrayList keeps track of all errors found
        ArrayList<String> errorList = new ArrayList<>();
        model.addAttribute("errors", errorList);

        // tempUser keeps track of email and password after the form is submitted
        model.addAttribute("tempUser", new UserClass());

        return "login/log_in";
    }

    @PostMapping("/login_user")
    public String loginUser(@ModelAttribute UserClass user, Model model, HttpSession session) {
        model.addAttribute("user", user);


        // Error ArrayList keeps track of all errors found
        ArrayList<String> errorList = new ArrayList<>();

        // tempUser keeps track of email and password after the form is submitted
        UserClass tempUser = new UserClass();
        tempUser.setEmail(user.getEmail());
        tempUser.setPassword(user.getPassword());

        boolean emailEmpty = user.getEmail().isEmpty();
        boolean emailNonexistent = service.findUser(user.getEmail()).isEmpty();
        boolean passwordEmpty = user.getPassword().isEmpty();
        boolean passwordMismatch = false;
        if (!emailNonexistent) {
            passwordMismatch = !service.verifyUser(user.getEmail(), user.getPassword());
        }

        // Email related errors
        if (emailEmpty) {
            errorList.add("emailEmpty");
            tempUser.setPassword("");

        }
        if (emailNonexistent) {
            errorList.add("emailNonexistent");
            tempUser.setEmail("");
            tempUser.setPassword("");

        }

        // Password related errors
        if (passwordEmpty) {
            errorList.add("passwordEmpty");
        }
        if (passwordMismatch) {
            errorList.add("passwordMismatch");
            tempUser.setPassword("");
        }

        // Prevent login if errors found
        if (!errorList.isEmpty()) {
            System.out.println(Arrays.toString(errorList.toArray()));

            model.addAttribute("errors", errorList.toArray());
            model.addAttribute("tempUser", tempUser);

            return "login/log_in";
        }

        // Store user details and redirect to homepage
        session.setAttribute(LocalStorage.LOGGED_IN_USER_ID,service.findUser(user.getEmail()).get());

        return "redirect:/";
    }

    //post req for logout
    @PostMapping("/logout_user")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/"; // redirect home
    }
}