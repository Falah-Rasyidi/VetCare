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
public class TransactionEntryController {
    private TransactionService service;
    private CartEntryService cartEntryService;

    @Autowired
    public TransactionEntryController(TransactionService service, CartEntryService cartEntryService) {

        this.service = service;
        this.cartEntryService = cartEntryService;
    }

    @PostMapping(value = "/transaction-details", params = {"transactionID"})
    public String debugTransEntry(@RequestParam(value = "transactionID") Integer transactionID, Model model) {
        System.out.println(transactionID);
        Collection<TransactionEntry> transactionEntries = service.getTransactionEntries(transactionID);
        model.addAttribute("transactionEntries", transactionEntries);
        return "debug-pages/transaction-details";
    }
}
