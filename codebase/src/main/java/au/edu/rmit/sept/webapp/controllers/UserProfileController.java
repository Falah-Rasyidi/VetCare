package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.models.PetClass;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.models.UserClass;
import au.edu.rmit.sept.webapp.services.PetService;
import au.edu.rmit.sept.webapp.services.UserService;
import au.edu.rmit.sept.webapp.util.EmailVerification;
import au.edu.rmit.sept.webapp.util.LocalStorage;
import au.edu.rmit.sept.webapp.util.smtpSenderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UserProfileController {

    private UserService userService;
    private PetService petService;
    private smtpSenderService senderService;

    @Autowired
    public UserProfileController(UserService userService, PetService petService, smtpSenderService senderService) {
        this.petService = petService;
        this.userService = userService;
        this.senderService = senderService;
    }

    @GetMapping("/user_profile")
    public String userProfile(Model model, HttpSession session) {
        // Ensure that user is logged in before accessing the profile
        User currentUser = (User) session.getAttribute(LocalStorage.LOGGED_IN_USER_ID);
        if (currentUser == null) {
            return "redirect:/login_user";
        }

        // Inject user and pet information into their profile
        UserClass tempUser = new UserClass();
        tempUser.setFirstName(currentUser.firstName());
        tempUser.setLastName(currentUser.lastName());
        tempUser.setEmail(currentUser.email());
        tempUser.setPhoneNumber(currentUser.phoneNumber());
        Collection<Pet> petList = petService.findByOwnerId(currentUser.userId().toString());

        model.addAttribute("tempUser", tempUser);
        model.addAttribute("petList", petList);

        // Add empty error ArrayList to prevent errors
        model.addAttribute("userErrors", new ArrayList<String>());
        model.addAttribute("petErrors", new ArrayList<String>());

        // Add other user types to prevent errors
        model.addAttribute("editUser", new UserClass());

        return "userProfile/profile_page";
    }

    @PostMapping("/user_profile/edit_profile")
    public String editProfile(@ModelAttribute UserClass editUser, Model model, HttpSession session) {
        model.addAttribute("editUser", editUser);
        User currentUser = (User) session.getAttribute(LocalStorage.LOGGED_IN_USER_ID);

        // Error ArrayList keeps track of all errors found
        ArrayList<String> editErrors = new ArrayList<>();

        // Regex to check phone number validity
        String regex = "^((\\d{3}) (\\d{3}) (\\d{3}))$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(editUser.getPhoneNumber().replace("+", " "));

        // tempUser used to keep track of form data
        UserClass tempUser = new UserClass();
        tempUser.setFirstName(currentUser.firstName());
        tempUser.setLastName(currentUser.lastName());
        tempUser.setEmail(currentUser.email());
        tempUser.setPhoneNumber(currentUser.phoneNumber());

        /*
         * Ensure that first name or last name field isn't empty
         * Ensure that email is valid and not taken
         * Ensure that phone number is correctly formatted
         */
        boolean firstNameEmpty = editUser.getFirstName().isBlank();
        boolean lastNameEmpty = editUser.getLastName().isBlank();
        boolean emailEmpty = editUser.getEmail().isBlank();
        boolean emailInvalid = !EmailVerification.verifyEmailFormat(editUser.getEmail());
        boolean emailDuplicate = userService.findUser(editUser.getEmail()).isPresent()
                && (!editUser.getEmail().equals(currentUser.email()));
        boolean invalidPhoneNumber = !matcher.matches();

        // Name related errors
        if (firstNameEmpty) {
            editErrors.add("firstNameEmpty");
            tempUser.setFirstName("");
        }
        if (lastNameEmpty) {
            editErrors.add("lastNameEmpty");
            tempUser.setLastName("");
        }

        // Email related errors
        if (emailEmpty) {
            editErrors.add("emailEmpty");
        }
        if (emailInvalid) {
            editErrors.add("emailInvalid");
            tempUser.setEmail("");
        }
        if (emailDuplicate) {
            editErrors.add("emailDuplicate");
            tempUser.setEmail("");
        }

        // Phone number related errors
        if (invalidPhoneNumber) {
            editErrors.add("invalidPhoneNumber");
            tempUser.setPhoneNumber("");
        }

        // Prevent edit if errors found
        if (!editErrors.isEmpty()) {
            System.out.println(Arrays.toString(editErrors.toArray()));

            model.addAttribute("userErrors", editErrors.toArray());
            model.addAttribute("tempUser", tempUser);

            return "userProfile/profile_page";
        }

        // If all details valid, edit user's details in database, update local storage,
        // and return to profile page
        userService.editUser(currentUser.userID(), editUser);
        session.setAttribute(LocalStorage.LOGGED_IN_USER_ID, userService.findUser(editUser.getEmail()).get());

        return "redirect:/user_profile";
    }

    @DeleteMapping("/user_profile/delete_profile")
    public String deleteProfile(HttpSession session) {
        // Remove user details from local storage
        User currentUser = (User) session.getAttribute(LocalStorage.LOGGED_IN_USER_ID);
        session.invalidate();

        // Delete user details and redirect to homepage
        userService.deleteUser(currentUser.userId());

        //Email user about account deletion
        senderService.sendEmail(
                currentUser.email(),
                "Confirmation of Account Deletion with Vetcare",
                "We're sad to see you leave us at Vetcare.\n"+
                        "If you have feedback to leave please contact us at sept.petcare@gmail.com");

        return "homepage/index";
    }

    @PostMapping("/user_profile/add_pet")
    public String addPet(@ModelAttribute PetClass newPet, Model model, HttpSession session) {
        model.addAttribute("newPet", newPet);
        User currentUser = (User) session.getAttribute(LocalStorage.LOGGED_IN_USER_ID);

        newPet.setUserId(currentUser.userId());
        petService.insertNewPet(newPet);

        return "redirect:/user_profile";
    }

    @PostMapping("/user_profile/edit_pet")
    public String editPet(@ModelAttribute PetClass editPet, Model model) {
        model.addAttribute("editPet", editPet);

        petService.editPet(editPet.getPetId(), editPet);

        return "redirect:/user_profile";
    }

    @PostMapping("/user_profile/delete_pet")
    public String deletePet(@ModelAttribute PetClass deletePet) {
        // Delete pet details
        petService.deletePet(deletePet.getPetId());

        return "redirect:/user_profile";
    }
}