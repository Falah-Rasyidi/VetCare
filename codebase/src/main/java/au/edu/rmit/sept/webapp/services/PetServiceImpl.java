package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.models.PetClass;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.models.UserClass;
import au.edu.rmit.sept.webapp.repositories.PetRepository;
import au.edu.rmit.sept.webapp.repositories.PetRepositoryImpl;
import au.edu.rmit.sept.webapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class PetServiceImpl implements PetService {
    private PetRepository repository;

    @Autowired
    public PetServiceImpl(PetRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<Pet> getPets() {
        return repository.findAll();
    }

    @Override
    public Collection<Pet> findByOwnerId(String ownerId) {
        return repository.findByOwnerId(ownerId);
    }

    @Override
    public Pet findById(Integer id) {return repository.findById(id);}

    @Override
    public Boolean insertNewPet(PetClass pet) {
        return repository.insertNewPet(pet);
    }

    @Override
    public Boolean editPet(int id, PetClass newPet) {
        return repository.editPet(id, newPet);
    }

    @Override
    public Boolean deletePet(int id) {
        return repository.deletePet(id);
    }
}
