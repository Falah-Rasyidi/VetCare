package au.edu.rmit.sept.webapp.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import au.edu.rmit.sept.webapp.models.Product;
import au.edu.rmit.sept.webapp.services.ProductService;

@Controller
//@RequestMapping("/store")
public class ProductsController {

    private ProductService service;

    @Autowired
    public ProductsController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/store")
    public String clinics(Model model) {
        Collection<Product> products = service.getProducts();
        model.addAttribute("products", products);
        return "store/store";
    }
}
