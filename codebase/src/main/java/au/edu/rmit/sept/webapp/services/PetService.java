package au.edu.rmit.sept.webapp.services;
import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.models.PetClass;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.models.UserClass;

import java.util.Collection;
import java.util.Optional;

public interface PetService {
    public Collection<Pet> getPets();
    public Collection<Pet> findByOwnerId(String ownerId);
    public Pet findById(Integer id);
    public Boolean insertNewPet(PetClass pet);
    public Boolean editPet(int id, PetClass newPet);
    public Boolean deletePet(int id);
}
