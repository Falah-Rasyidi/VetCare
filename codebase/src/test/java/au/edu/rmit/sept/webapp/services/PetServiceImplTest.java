package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.Availability;
import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.models.PetClass;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PetServiceImplTest {
    private Integer testPetId = 0;
    @Autowired
    PetServiceImpl petService;
    @Test
    void getPets() {
        Collection<Pet> pets = petService.getPets();
        assertNotNull(pets);
    }

    @Test
    void findByOwnerId() {
        Collection<Pet> pets = petService.findByOwnerId("1");
        assertFalse(pets.isEmpty());
    }
    //Test A & B are used here to enforce running order
    @Test
    void testA_insertNewPet() {
        Collection<Pet> pets = petService.getPets();
        Integer originalSize = pets.size();

        //Inserting new pet
        PetClass newPet = (new PetClass(1, 1,"Test","N/A","Test","Test","2000-01-01"));
        assertTrue((petService.insertNewPet(newPet)));

        //Validating pet was inserted
        pets = petService.getPets();
        assertEquals(originalSize+1,pets.size());

    }
    @Test
    @Order(2)
    void testB_deletePet() {
        //Get Current Availabilities
        Collection<Pet> pets = petService.getPets();
        Integer originalSize = pets.size();

        //Store latest availability for re-insertion
        Pet res = (Pet) pets.toArray()[pets.toArray().length-1];
        Integer originalId = res.petId();

        //Delete latest availability
        assertTrue(petService.deletePet(originalId));

        //Verify latest availability
        pets = petService.getPets();
        res = (Pet) pets.toArray()[pets.toArray().length-1];
        assertNotEquals(res.petId(), originalId);
    }
}