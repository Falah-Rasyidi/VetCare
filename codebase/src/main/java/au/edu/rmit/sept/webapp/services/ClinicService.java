package au.edu.rmit.sept.webapp.services;

import java.util.Collection;

import au.edu.rmit.sept.webapp.models.Clinic;

public interface ClinicService {

    public Collection<Clinic> getClinics();

    // overloaded with sort
    public Collection<Clinic> getClinics(String sort);

    public Clinic getByAvailabilityId(Integer availabilityId);
}
