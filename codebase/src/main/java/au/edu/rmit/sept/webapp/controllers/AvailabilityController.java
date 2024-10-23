package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.*;
import au.edu.rmit.sept.webapp.services.AvailabilityService;
import au.edu.rmit.sept.webapp.services.ClinicService;
import au.edu.rmit.sept.webapp.services.PetService;
import au.edu.rmit.sept.webapp.util.LocalStorage;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AvailabilityController {
    private final AvailabilityService availabilityService;
    private final PetService petService;
    private final ClinicService clinicService;

    @Autowired
    public AvailabilityController(AvailabilityService availabilityService, PetService petService, ClinicService clinicService) {
        this.availabilityService = availabilityService;
        this.petService = petService;
        this.clinicService = clinicService;
    }

    @GetMapping("/availability")
    public String index(Model model, HttpSession session) {
        User newUser = (User) session.getAttribute(LocalStorage.LOGGED_IN_USER_ID);
        if (newUser == null) {
            return "redirect:/";
        }

        Collection<Pet> pets = petService.findByOwnerId(String.valueOf(newUser.userID()));
        if (pets.isEmpty()) {
            return "redirect:/";
        }
        model.addAttribute("pets", pets);

        Collection<Availability> availabilities = availabilityService.getValidAvailability();
        if (availabilities.isEmpty()) {
            return "redirect:/";
        }

        // map clinics (From Clinics and Availability Models) and merge duplicate keys
        Map<Integer, Clinic> clinicMap = new HashMap<>();
        for (Availability availability : availabilities) {
            int clinicId = availability.clinicID();
            // only if not already present
            if (!clinicMap.containsKey(clinicId)) {
                Clinic clinic = clinicService.getByAvailabilityId(availability.availabilityId());
                clinicMap.put(clinicId, clinic);
            }
        }

        model.addAttribute("availability", availabilities);
        model.addAttribute("clinicMap", clinicMap);

        BookingClass newBooking = new BookingClass(0, 0, 0, 0, "");
        model.addAttribute("newBooking", newBooking);

        return "appointments/bookings";
    }
}