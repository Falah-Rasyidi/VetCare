package au.edu.rmit.sept.webapp.controllers.debug;

import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.models.PetClass;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.services.PetService;
import au.edu.rmit.sept.webapp.util.smtpSenderService;
import au.edu.rmit.sept.webapp.util.LocalStorage;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

@Controller
public class PetDebugController {
    private PetService service;
    private smtpSenderService senderService;
    // Automatically connects Controller to User Service and constructs it
    @Autowired
    public PetDebugController(PetService service, smtpSenderService senderService) {
        this.service = service;
        this.senderService = senderService;
    }

    // GET Mapping for general access of URL
    @GetMapping("/debug-pet")
    public String debugPage(Model model, HttpSession session) throws MessagingException {

        User user = (User) session.getAttribute(LocalStorage.LOGGED_IN_USER_ID);
        if(user == null) {
            // ADD LOGIC FOR NO USER
            return "redirect:/";
        }
        Collection<Pet> pets = service.getPets(); // Get all users from DB
        model.addAttribute("pets", pets);
        model.addAttribute("newpet", new PetClass()); // Construct null user to insert details into
        return "debug-pages/pet-debug";
    }

    // POST Mapping for POST request from form submission
    @PostMapping("/debug-pet")
    public String debugPageFormSubmit(@ModelAttribute("user") PetClass newpet, Model model, HttpSession session) {
        // NOTE RIGHT NOW THIS THING PRETTY MUCH REDIRECTS TO THE ORIGINAL PAGE WITH NEW USERS
        // IT SHOULD GO TO FURTHER PAGES
        User user = (User) session.getAttribute(LocalStorage.LOGGED_IN_USER_ID);
        if(user == null) {
            // ADD LOGIC FOR NO USER
            return "redirect:/";
        }
        System.out.println(newpet);
        newpet.setUserId(((User) session.getAttribute(LocalStorage.LOGGED_IN_USER_ID)).userID());
        service.insertNewPet(newpet);

        //Example email sending with user from HTTPSession
        senderService.sendEmail(
                user.email(),
                String.format("Confirmation of new pet, %s", newpet.getName()),
                String.format("You have successfully added %s the %s to your account",newpet.getName(),newpet.getSpecies()));

        Collection<Pet> pets = service.getPets(); // Get all users from DB
        model.addAttribute("pets", pets);
        model.addAttribute("newpet", new PetClass()); // Construct null user to insert details into
        return "debug-pages/pet-debug";
    }
}
