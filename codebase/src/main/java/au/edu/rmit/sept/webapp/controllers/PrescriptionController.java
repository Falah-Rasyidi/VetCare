package au.edu.rmit.sept.webapp.controllers;

import java.util.Collection;

import au.edu.rmit.sept.webapp.models.*;
import au.edu.rmit.sept.webapp.services.CartEntryService;
import au.edu.rmit.sept.webapp.services.PetService;
import au.edu.rmit.sept.webapp.services.PrescriptionService;
import au.edu.rmit.sept.webapp.util.LocalStorage;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/prescriptions")
public class PrescriptionController {

    private PrescriptionService prescriptionService;
    private PetService petService;
    private CartEntryService cartService;

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService, PetService petService, CartEntryService cartService) {
        this.prescriptionService = prescriptionService;
        this.petService = petService;
        this.cartService = cartService;
    }

    @GetMapping
    public String prescriptions(Model model, HttpSession session) {
        Collection<Prescription> prescriptions = prescriptionService.getAllPrescriptions();
        model.addAttribute("prescriptions", prescriptions);
        User currentUser = (User) session.getAttribute(LocalStorage.LOGGED_IN_USER_ID);
        if (currentUser == null) {
            return "redirect:/login_user";
        }

        // Inject user and pet information into their profile
        Collection<Pet> petLists = petService.findByOwnerId(currentUser.userId().toString());

        model.addAttribute("petLists", petLists);
        return "prescriptions/prescriptions";
    }

    @PostMapping("/add_prescription")
    public String addPrescription(HttpSession session, @RequestParam("id") Integer id, @RequestParam("quantity") int quantity) {
        User user = (User) session.getAttribute(LocalStorage.LOGGED_IN_USER_ID);

        CartEntryClass cartEntry = new CartEntryClass(
                user.userId(),
                id,
                quantity
        );

        cartService.addCartEntry(cartEntry);

        return "redirect:/prescriptions";
    }

    @GetMapping("/{id}")
    public String prescriptionDetail(@PathVariable("id") Integer id, Model model) {
        Prescription prescription = prescriptionService.getPrescriptionById(id);
        model.addAttribute("prescription", prescription);
        return "prescriptions/prescriptionDetail";
    }
}
