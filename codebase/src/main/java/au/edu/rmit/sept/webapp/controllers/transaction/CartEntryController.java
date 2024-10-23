package au.edu.rmit.sept.webapp.controllers.transaction;

import au.edu.rmit.sept.webapp.models.CartEntry;
import au.edu.rmit.sept.webapp.models.CartEntryClass;
import au.edu.rmit.sept.webapp.services.CartEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;

@Controller
public class CartEntryController {
    private CartEntryService cartEntryService;

    @Autowired
    public CartEntryController(CartEntryService cartEntryService) {
        this.cartEntryService = cartEntryService;
    }

    @GetMapping("/debug-cart-entry")
    public String debugCartEntry(Model model) {
        Collection<CartEntry> cartEntries = cartEntryService.getCartEntries();
        CartEntryClass newCartEntry = new CartEntryClass();
        model.addAttribute("cartEntries", cartEntries);
        model.addAttribute("newCartEntry", newCartEntry);
        return "debug-pages/cart-entry";
    }
    @PostMapping("/debug-cart-entry")
    public String debugCartEntry(@ModelAttribute("newCartEntry") CartEntryClass newCartEntry) {
        cartEntryService.addCartEntry(newCartEntry);
        return "redirect:/debug-cart-entry";
    }
}
