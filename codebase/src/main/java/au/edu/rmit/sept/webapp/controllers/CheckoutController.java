package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.CartEntry;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.services.CartEntryService;
import au.edu.rmit.sept.webapp.services.TransactionService;
import au.edu.rmit.sept.webapp.util.LocalStorage;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Controller
public class CheckoutController {

    private TransactionService transactionService;
    private CartEntryService cartEntryService;

    @Autowired
    public CheckoutController(TransactionService transactionService, CartEntryService cartEntryService) {
        this.transactionService = transactionService;
        this.cartEntryService = cartEntryService;
    }

    @GetMapping("/checkout")
    public String checkout() {
        return "checkout/checkout";
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam String number, @RequestParam String date, HttpSession session) {
        User currentUser = (User) session.getAttribute(LocalStorage.LOGGED_IN_USER_ID);
        // Use Luhn's algorithm to check card number validity. Code from https://www.geeksforgeeks.org/luhn-algorithm/
        int nDigits = number.length();

        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--) {

            int d = number.charAt(i) - '0';

            if (isSecond) {
                d = d * 2;
            }

            // We add two digits to handle
            // cases that make two digits
            // after doubling
            nSum += d / 10;
            nSum += d % 10;

            isSecond = !isSecond;
        }

        // Check validity of expiration date
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate formatDate = LocalDate.parse(date, format);

        boolean cardExpired = formatDate.isBefore(LocalDate.now());

        // Reset fields if error occurs
        if (cardExpired || nSum % 10 != 0) {
            return "checkout/checkout";
        }
        else {
            // Delete user cart entries from database and return to homepage
            Collection<CartEntry> cartEntries = cartEntryService.getCartEntriesByUser(currentUser.userId());
            transactionService.convertCartEntryToTransaction(cartEntries, currentUser.userId());

            return "thankyou/thankyou";
        }
    }
}
