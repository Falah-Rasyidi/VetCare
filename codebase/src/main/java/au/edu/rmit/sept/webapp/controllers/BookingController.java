package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.*;
import au.edu.rmit.sept.webapp.services.AvailabilityService;
import au.edu.rmit.sept.webapp.services.BookingService;
import au.edu.rmit.sept.webapp.services.ClinicService;
import au.edu.rmit.sept.webapp.services.PetService;
import au.edu.rmit.sept.webapp.util.LocalStorage;
import au.edu.rmit.sept.webapp.util.smtpSenderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
public class BookingController {
    private AvailabilityService availabilityService;
    private BookingService bookingService;
    private ClinicService clinicService;

    //Email exclusive services
    private smtpSenderService senderService;
    private PetService petService;


    // Automatically connects Controller to User Service and constructs it
    @Autowired
    public BookingController(
            AvailabilityService availabilityService,
            BookingService bookingService,
            ClinicService clinicService,
            smtpSenderService senderService,
            PetService petService) {
        this.availabilityService = availabilityService;
        this.bookingService = bookingService;
        this.clinicService = clinicService;
        this.senderService = senderService;
        this.petService = petService;
    }

    @PostMapping("/bookings")
    public String bookingPageFormSubmit(@ModelAttribute("booking") BookingClass newBooking, HttpSession session) {

        // Insert session userID into booking
        User newUser = (User) session.getAttribute(LocalStorage.LOGGED_IN_USER_ID);
        newBooking.setCustomerId(newUser.userID());

        System.out.println(newBooking.getAvailabilityId());
        Clinic availabilityClinic = clinicService.getByAvailabilityId(newBooking.getAvailabilityId());
        newBooking.setClinicId(availabilityClinic.clinicId());

        newBooking.setStatus("Booked");

        System.out.println(newBooking);

        //Toggle availability to booked
        availabilityService.bookAvailability(newBooking.getAvailabilityId());

        //Insert booking
        bookingService.addBooking(newBooking);

        //Get details for mail formatting
        Pet newPet = petService.findById(newBooking.getPetId());
        Availability usedAvailablility = availabilityService.getAvailabilityById(newBooking.getAvailabilityId());
//        System.out.println(newPet);
        //Email user about booking success
        senderService.sendEmail(
                newUser.email(),
                String.format("Confirmation of Booking with %s ",newBooking.getClinicId()),
                "Booking Confirmation:"+
                        String.format("\nPet:   %s",newPet.name())+
                        String.format("\nClinic:    %s", availabilityClinic.clinic())+
                        String.format("\nFrom:  %s",usedAvailablility.dateTimeStart())+
                        String.format("\nTill:  %s",usedAvailablility.dateTimeEnd())+
                        "\n\n Please arrive 15 minutes before your alloted booking time."
                        + "\n\nIf you have feedback to leave please contact us at sept.petcare@gmail.com");

        return "redirect:/view-bookings"; //Should lead to booking confirmation page
    }

    @GetMapping("/view-bookings")
    public String index(Model model, HttpSession session) {
        User newUser = (User) session.getAttribute(LocalStorage.LOGGED_IN_USER_ID);

        // get all bookings for the specified user
        Collection<BookingClass> detailedBookings = bookingService.findDetailedBookingsByCustomerId(newUser.userID());

        if (detailedBookings.isEmpty()) {
            return "redirect:/";
        }

        // mapping to store any available bookings, based on specified clinic
        Map<Integer, Collection<Availability>> clinicAvailabilities = new HashMap<>();

        // fetch for any available bookings, based on specified clinic
        for (BookingClass booking : detailedBookings) {
            Collection<Availability> availabilities = availabilityService.getAvailabilitiesByClinicId(booking.getClinicId());
            clinicAvailabilities.put(booking.getClinicId(), availabilities);
        }

        // add bookings and availability data to the model
        model.addAttribute("bookings", detailedBookings);
        model.addAttribute("clinicAvailabilities", clinicAvailabilities);

        return "appointments/view-bookings";
    }

    @PostMapping("/delete-booking")
    public String deleteBooking(@RequestParam("availabilityId") int availabilityId) {
        //toggle availability to available
        availabilityService.putAvailability(availabilityId);

        //remove booking
        bookingService.removeBooking(availabilityId);
        // reload view bookings page
        return "redirect:/view-bookings";
    }

    @PostMapping("/reschedule-booking")
    public String rescheduleBooking(@RequestParam("bookingId") int bookingId,
                                    @RequestParam("oldAvailabilityId") int oldAvailabilityId,
                                    @RequestParam("selectedAvailability") int selectedAvailabilityId) {

        System.out.println("BOOKING ID: " + bookingId + "\nOLD AVAIL ID: " + oldAvailabilityId +"\nNEW AVAIL ID " + selectedAvailabilityId);

        // set booked status of new availability to TRUE
        availabilityService.bookAvailability(selectedAvailabilityId);
        // set booked status of old availability to FALSE
        availabilityService.putAvailability(oldAvailabilityId);
        //reschedule/switch the availability of the selected booking
        bookingService.rescheduleBooking(bookingId, selectedAvailabilityId);

        // Redirect back to the view bookings page
        return "redirect:/view-bookings";
    }
}
