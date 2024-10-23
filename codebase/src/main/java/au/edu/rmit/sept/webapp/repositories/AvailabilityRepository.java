package au.edu.rmit.sept.webapp.repositories;
import java.util.Collection;
import java.util.List;
import au.edu.rmit.sept.webapp.models.Availability;
import au.edu.rmit.sept.webapp.models.AvailabilityClass;
import au.edu.rmit.sept.webapp.models.UserClass;

public interface AvailabilityRepository {
    public List<Availability> findAll();
    public Boolean bookAvailability(Integer availabilityId);
    public List<Availability> findValidAvailability();

    public Boolean putAvailability(Integer availabilityId);
    public List<Availability> findAvailabilityByClinicId(Integer clinicId);

    public Boolean addAvailability(AvailabilityClass availability);
    public Boolean removeAvailability(Integer availabilityId);

    public Availability findAvailabilityById(Integer availabilityId);
}