package au.edu.rmit.sept.webapp.controllers.transaction;

import au.edu.rmit.sept.webapp.models.*;
import au.edu.rmit.sept.webapp.services.CartEntryService;
import au.edu.rmit.sept.webapp.services.TransactionService;
import au.edu.rmit.sept.webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@Controller
public class TransactionController {
    private TransactionService service;
    private CartEntryService cartEntryService;

    @Autowired
    public TransactionController(TransactionService service, CartEntryService cartEntryService) {

        this.service = service;
        this.cartEntryService = cartEntryService;
    }

    @GetMapping("/debug-trans-entry")
    public String debugTransEntry(Model model) {
        Collection<Transaction> transactions = service.getTransactions();
        model.addAttribute("transactions", transactions);

        Collection<CartEntry> cartEntries = cartEntryService.getCartEntries();
        model.addAttribute("cartEntries", cartEntries);
        return "debug-pages/transaction-entry";
    }
    @PostMapping(value = "/debug-trans-entry", params = {"userId"})
    public String debugTransEntry(@RequestParam(value = "userId") Integer userId) {
        System.out.println(userId);
        Collection<CartEntry> cartEntries = cartEntryService.getCartEntriesByUser(userId);
        if(cartEntries.isEmpty()){
            return "redirect:/debug-trans-entry";
        }
        service.convertCartEntryToTransaction(cartEntries, userId);
        System.out.println("Transacted");
        return "redirect:/debug-trans-entry";
    }
}
