package au.edu.rmit.sept.webapp.services;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.edu.rmit.sept.webapp.models.Clinic;
import au.edu.rmit.sept.webapp.repositories.ClinicRepository;

@Service
public class ClinicServiceImpl implements ClinicService {
    private ClinicRepository repository;

    @Autowired
    public ClinicServiceImpl(ClinicRepository repository) {
        this.repository = repository;
    }

    // The original getClinics method
    @Override
    public Collection<Clinic> getClinics() {
        return repository.getAll();
    }

    // Overloaded getClinics method with sorting functionality
    @Override
    public Collection<Clinic> getClinics(String sort) {
        List<Clinic> clinics = repository.getAll();

        // Perform sorting based on the `sort` parameter
        if (sort != null) {
            switch (sort) {
                case "name_asc":
                    clinics.sort(Comparator.comparing(Clinic::clinic));
                    break;
                case "name_desc":
                    clinics.sort(Comparator.comparing(Clinic::clinic).reversed());
                    break;
                // Add rating sorting logic if needed
                // case "rating_high":
                //     clinics.sort(Comparator.comparing(Clinic::getRating).reversed());
                //     break;
                // case "rating_low":
                //     clinics.sort(Comparator.comparing(Clinic::getRating));
                //     break;
            }
        }

        return clinics;
    }
    @Override
    public Clinic getByAvailabilityId(Integer availabilityId){
        return repository.getByAvailability(availabilityId);
    }
}