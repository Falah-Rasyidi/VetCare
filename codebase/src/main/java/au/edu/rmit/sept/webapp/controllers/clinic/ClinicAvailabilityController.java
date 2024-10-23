package au.edu.rmit.sept.webapp.controllers.clinic;

import au.edu.rmit.sept.webapp.models.Availability;
import au.edu.rmit.sept.webapp.models.AvailabilityClass;
import au.edu.rmit.sept.webapp.models.Clinic;
import au.edu.rmit.sept.webapp.services.AvailabilityService;
import au.edu.rmit.sept.webapp.util.dataTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;

@Controller
public class ClinicAvailabilityController {
    private final AvailabilityService availabilityService;

    @Autowired
    public ClinicAvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @GetMapping("/debug/availability")
    public String addAvailabilityView(Model model) {
        Collection<Availability> availabilities = availabilityService.getAvailability();
        AvailabilityClass newAvailabilityClass = new AvailabilityClass();
        model.addAttribute("availabilities", availabilities);
        model.addAttribute("newAvailability", newAvailabilityClass);
        return ("debug-pages/insert-availability-debug");
    }
    @PostMapping("/debug/availability")
    public String addAvailability(Model model, AvailabilityClass newAvailability) {
        newAvailability.setDateTimeStart(dataTypeConverter.htmlTimeToSQL(newAvailability.getDateTimeStart()));
        newAvailability.setDateTimeEnd(dataTypeConverter.htmlTimeToSQL(newAvailability.getDateTimeEnd()));
        newAvailability.setBookedStatus(false);
        System.out.println(newAvailability.toInsertSQL());
        availabilityService.addAvailability(newAvailability);
        Collection<Availability> availabilities = availabilityService.getAvailability();
        AvailabilityClass newAvailabilityClass = new AvailabilityClass();
        model.addAttribute("availabilities", availabilities);
        model.addAttribute("newAvailability", newAvailability);
        return ("debug-pages/insert-availability-debug");
    }
}
