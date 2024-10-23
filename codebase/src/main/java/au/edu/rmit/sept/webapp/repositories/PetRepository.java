package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.*;

import java.util.List;
import java.util.Optional;

public interface PetRepository {
    public List<Pet> findAll();
    public List<Pet> findByOwnerId(String ownerID);
    public Pet findById(Integer id);
    public Boolean insertNewPet(PetClass pet);
    public Boolean editPet(int id, PetClass pet);
    public Boolean deletePet(Integer id);
}
