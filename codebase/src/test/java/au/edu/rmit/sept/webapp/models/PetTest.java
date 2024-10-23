package au.edu.rmit.sept.webapp.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PetTest {

    @Test
    void constructorTest() {
        Pet pet1 = new Pet(1, 1, "Rupert", "Male", "Spider", "Redback", "01/01/1970");
        assertEquals(1, pet1.petId());
    }
}
