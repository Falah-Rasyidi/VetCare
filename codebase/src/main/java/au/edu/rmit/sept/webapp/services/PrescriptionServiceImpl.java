package au.edu.rmit.sept.webapp.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.edu.rmit.sept.webapp.models.Prescription;
import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.repositories.PrescriptionRepository;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {
    private PrescriptionRepository repository;

    @Autowired
    public PrescriptionServiceImpl(PrescriptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<Prescription> getAllPrescriptions() {
        return repository.getAll();
    }

    @Override
    public Prescription getPrescriptionById(Integer prescriptionId) {
        return repository.getById(prescriptionId);
    }

    @Override
    public Collection<Pet> findByOwnerId(String ownerId) {
        return repository.findByOwnerId(ownerId);
    }
}
