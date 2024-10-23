package au.edu.rmit.sept.webapp.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AvailabilityTest {

    @Test
    void constructorTest() {
        Availability availability1 = new Availability(1, 1, "DD-MM-YYY-HH-MM-SS","DD-MM-YYY-HH-MM-SS",false);
        assertEquals(1, availability1.availabilityId());
    }
}
