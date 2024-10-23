package au.edu.rmit.sept.webapp.controllers;

import at.favre.lib.crypto.bcrypt.BCrypt;
import au.edu.rmit.sept.webapp.models.UserClass;
import au.edu.rmit.sept.webapp.services.UserService;
import au.edu.rmit.sept.webapp.util.LocalStorage;
import au.edu.rmit.sept.webapp.util.smtpSenderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class SignUpController {
    private UserService service;
    private smtpSenderService senderService;

    @Autowired
    SignUpController(UserService service, smtpSenderService senderService) {
        this.service = service;
        this.senderService = senderService;

    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("newUser", new UserClass());

        // Error ArrayList keeps track of all errors found
        ArrayList<String> errorList = new ArrayList<>();
        model.addAttribute("errors", errorList);

        // tempUser keeps track of email, password, and confirm password after the form is submitted
        model.addAttribute("tempUser", new UserClass());

        return "signup/sign_up";
    }

    @PostMapping("/signup")
    public String addUser(@ModelAttribute UserClass newUser, Model model, HttpSession session) {
        model.addAttribute("newUser", newUser);

        // Error ArrayList keeps track of all errors found
        ArrayList<String> signupErrors = new ArrayList<>();

        // tempUser keeps track of password and confirm password because they reset after the form is submitted
        UserClass tempUser = new UserClass();
        tempUser.setEmail(newUser.getEmail());
        tempUser.setPassword(newUser.getPassword());
        tempUser.setConfirmPassword(newUser.getConfirmPassword());
        tempUser.setPhoneNumber(newUser.getPhoneNumber());

        /*
         * Ensure that password fields aren't empty
         * Ensure that passwords aren't mismatched
         * Ensure that email field isn't empty
         * Ensure that email isn't already in use
         */
        boolean passwordEmpty = newUser.getPassword().isEmpty();
        boolean confirmPasswordEmpty = newUser.getConfirmPassword().isEmpty();
        boolean passwordMismatch = (!passwordEmpty && !confirmPasswordEmpty) && (!newUser.getPassword().equals(newUser.getConfirmPassword()));
        boolean emailDuplicate = service.findUser(newUser.getEmail()).isPresent();
        boolean emailEmpty = newUser.getEmail().isEmpty();

        final int PHONE_NUMBER_LENGTH = 9;
        String phoneNumber = newUser.getPhoneNumber().replaceAll("\\s", "");
        phoneNumber = phoneNumber.charAt(0) == '0' ? phoneNumber.substring(1) : phoneNumber;
        boolean phoneNumberInvalid = phoneNumber.length() != PHONE_NUMBER_LENGTH;

        // Password related errors
        if (passwordEmpty) {
            signupErrors.add("passwordEmpty");
        }
        if (confirmPasswordEmpty) {
            signupErrors.add("confirmPasswordEmpty");
        }
        if (passwordMismatch) {
            signupErrors.add("passwordMismatch");
            tempUser.setConfirmPassword("");
        }

        // Email related errors
        if (emailEmpty) {
            signupErrors.add("emailEmpty");
        }
        if (emailDuplicate) {
            signupErrors.add("emailDuplicate");
            tempUser.setEmail("");
        }

        // Phone number related errors
        if (phoneNumberInvalid) {
            signupErrors.add("invalidPhoneNumber");
        }

        // Prevent sign-up if errors found
        if (!signupErrors.isEmpty()) {
            System.out.println(Arrays.toString(signupErrors.toArray()));

            model.addAttribute("errors", signupErrors.toArray());
            model.addAttribute("tempUser", tempUser);

            return "signup/sign_up";
        }

        // If all details valid, add user to database and redirect to homepage
        String passwordHash = BCrypt.withDefaults().hashToString(12, newUser.getPassword().toCharArray());

        service.addUser(new UserClass(
                newUser.getFirstName(),
                newUser.getLastName(),
                passwordHash,
                phoneNumber,
                newUser.getEmail()
        ));

        // Store user details and redirect to homepage
        session.setAttribute(LocalStorage.LOGGED_IN_USER_ID, service.findUser(newUser.getEmail()).get());

        // Email confirmation
        senderService.sendEmail(
                newUser.getEmail(),
                "Confirmation of Account Creation with Vetcare",
                "Thank you for making an account with Vetcare.\n"+
                "We strive to give you access to vets when you need them\n\n"+
                "If you did not create this account please contact us at sept.petcare@gmail.com");

        return "redirect:/";
    }
}