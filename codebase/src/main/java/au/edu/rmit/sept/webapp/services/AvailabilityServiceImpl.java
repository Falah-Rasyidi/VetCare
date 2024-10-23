package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.Availability;
import au.edu.rmit.sept.webapp.models.AvailabilityClass;
import au.edu.rmit.sept.webapp.models.Clinic;
import au.edu.rmit.sept.webapp.repositories.AvailabilityRepository;
import au.edu.rmit.sept.webapp.repositories.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {
    private AvailabilityRepository repository;

    @Autowired
    public AvailabilityServiceImpl(AvailabilityRepository repository) {
        this.repository = repository;
    }

    // The original getClinics method
    @Override
    public Collection<Availability> getAvailability() {
        return repository.findAll();
    }
    @Override
    public Boolean bookAvailability(Integer availabilityId){return repository.bookAvailability(availabilityId);}
    @Override
    public Collection<Availability> getValidAvailability(){return repository.findValidAvailability();}
    @Override

    public Boolean putAvailability(Integer availabilityId){return repository.putAvailability(availabilityId);}
    @Override
    public Collection<Availability> getAvailabilitiesByClinicId(Integer clinicId){return repository.findAvailabilityByClinicId(clinicId);}

    public Boolean addAvailability(AvailabilityClass availability){return repository.addAvailability(availability);}
    @Override
    public Boolean removeAvailability(Integer availabilityId){return repository.removeAvailability(availabilityId);}

    @Override
    public Availability getAvailabilityById(Integer availabilityId) {return repository.findAvailabilityById(availabilityId);}

}