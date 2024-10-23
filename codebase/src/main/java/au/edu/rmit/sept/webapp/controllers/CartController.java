package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.CartEntryClass;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.services.CartEntryService;
import au.edu.rmit.sept.webapp.util.LocalStorage;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class CartController {

    private CartEntryService cartEntryService;

    @Autowired
    public CartController(CartEntryService cartEntryService) {
        this.cartEntryService = cartEntryService;
    }

    @GetMapping("cart")
    public String cart(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute(LocalStorage.LOGGED_IN_USER_ID);
        if (currentUser == null) {
            return "redirect:/login";
        }

        // Grab list of prescriptions that the user bought
        Collection<CartEntryClass> userPrescriptions = cartEntryService.convertEntriesToPrescriptions(currentUser.userId());

        // Calculate subtotal
        double subtotal = 0;
        for (CartEntryClass entry : userPrescriptions) {
            subtotal += entry.getPrice() * entry.getQuantity();
        }

        model.addAttribute("userPrescriptions", userPrescriptions);
        model.addAttribute("subtotal", subtotal);

        return "cart/cart";
    }
}
