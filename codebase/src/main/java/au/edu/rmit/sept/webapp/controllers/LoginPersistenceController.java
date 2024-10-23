package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.services.ClinicService;
import au.edu.rmit.sept.webapp.util.smtpSenderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import au.edu.rmit.sept.webapp.services.BookingService;
import au.edu.rmit.sept.webapp.services.PetService;

import au.edu.rmit.sept.webapp.util.LocalStorage;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.models.*;
import org.springframework.ui.Model;

import java.util.Collection;

// "Global" type controller (for all the thymeleaf templates)
@ControllerAdvice
public class LoginPersistenceController {

    private final BookingService bookingService;
    private final PetService petService;
    private final HttpSession session;

    @Autowired
    public LoginPersistenceController(HttpSession session, BookingService bookingService, PetService petService) {
        this.session = session;
        this.bookingService = bookingService;
        this.petService = petService;
    }

    // runs before every controller method and adds the currently logged-in user to model
    @ModelAttribute("loggedInUser")
    public User getLoggedInUser() {
        // retrieve logged in user:
        return (User) session.getAttribute(LocalStorage.LOGGED_IN_USER_ID);
    }

    @ModelAttribute
    public void addGlobalAttributes(Model model) {
        User loggedInUser = (User) session.getAttribute(LocalStorage.LOGGED_IN_USER_ID);

        if (loggedInUser != null) {
            try {
                // Get the user ID as an int from the User object
                int loggedUserId = loggedInUser.userId();  // Assuming User has getId() method

                // Convert the int ID to String since findByOwnerId requires a String
                String loggedUserIdStr = String.valueOf(loggedUserId);

                // Now pass the String version of the user ID to findByOwnerId
                Collection<Pet> pets = petService.findByOwnerId(loggedUserIdStr);
                boolean hasPets = !pets.isEmpty();
                boolean hasBookings = !bookingService.findDetailedBookingsByCustomerId(loggedUserId).isEmpty();

                // Add these attributes to the model
                model.addAttribute("hasPets", hasPets);
                model.addAttribute("hasBookings", hasBookings);
            } catch (Exception e) {
                System.err.println("Error processing the logged-in user data: " + e.getMessage());
            }
        }
    }
}