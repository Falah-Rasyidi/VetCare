package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.Availability;
import au.edu.rmit.sept.webapp.models.AvailabilityClass;
import au.edu.rmit.sept.webapp.models.Pet;
//import au.edu.rmit.sept.webapp.models.Clinic;

import java.util.Collection;

public interface AvailabilityService {
    public Collection<Availability> getAvailability();
    public Boolean bookAvailability(Integer availabilityId);
    public Collection<Availability> getValidAvailability();

    public Boolean putAvailability(Integer availabilityId);
    public Collection<Availability> getAvailabilitiesByClinicId(Integer clinicId);

    public Boolean addAvailability(AvailabilityClass availability);
    public Boolean removeAvailability(Integer availabilityId);

    public Availability getAvailabilityById(Integer availabilityId);


    // overloaded with sort
    // MAKE THIS FEATURE LATER
//    public Collection<Availability> getClinics(String sort);

}
