package au.edu.rmit.sept.webapp.repositories;

import java.util.List;
import au.edu.rmit.sept.webapp.models.Clinic;

public interface ClinicRepository {
    public List<Clinic> getAll();
    public Clinic getByAvailability(Integer availabilityId);
}
