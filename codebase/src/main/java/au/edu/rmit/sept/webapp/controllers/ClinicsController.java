package au.edu.rmit.sept.webapp.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import au.edu.rmit.sept.webapp.models.Clinic;
import au.edu.rmit.sept.webapp.services.ClinicService;

@Controller
//@RequestMapping("/clinics")
public class ClinicsController {

    private ClinicService service;

    @Autowired
    public ClinicsController(ClinicService service) {
        this.service = service;
    }

    @GetMapping("/clinics")
    public String clinics(@RequestParam(value = "sort", required = false) String sort, Model model) {
        Collection<Clinic> clinics = service.getClinics(sort);  // Sort based on user selection
        model.addAttribute("clinics", clinics);
        model.addAttribute("sort", sort);  // Pass the sort option to the view
        return "clinics/clinics";
    }
}
