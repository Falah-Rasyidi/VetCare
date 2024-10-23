package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.Availability;
import au.edu.rmit.sept.webapp.models.AvailabilityClass;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.accessibility.AccessibleValue;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@SpringBootTest

class AvailabilityServiceImplTest {
    @Autowired
    private AvailabilityService service;
    @Test
    void getAvailability() {
        Collection<Availability> availabilities = service.getAvailability();
        assertNotNull(availabilities);
    }

    @Test
    void testA_insertNewAvailability() {
        //Get Current Availabilities
        Collection<Availability> availabilities = service.getAvailability();
        Integer originalSize = availabilities.size();

        //Insert New Availability
        AvailabilityClass testAvailability = new AvailabilityClass(1, "2024-01-01 00:00:00", "2024-01-01 00:00:30", false);
        assertTrue(service.addAvailability(testAvailability));

        //Verify new availability exists
        availabilities = service.getAvailability();
        assertEquals(originalSize+1,availabilities.size());

        //Get ID of new Availability
        Availability res = (Availability) availabilities.toArray()[availabilities.toArray().length-1];
        Integer testAvailabilityId = res.availabilityId();
        System.out.println(testAvailabilityId);
    }

    @Test
    void testB_deleteAvailability() {
        //Get Current Availabilities
        Collection<Availability> availabilities = service.getAvailability();
        Integer originalSize = availabilities.size();

        //Store latest availability for re-insertion
        Availability res = (Availability) availabilities.toArray()[availabilities.toArray().length-1];
        Integer originalId = res.availabilityId();

        //Delete latest availability
        assertTrue(service.removeAvailability(res.availabilityId()));

        //Verify latest availability
        availabilities = service.getAvailability();
        res = (Availability) availabilities.toArray()[availabilities.toArray().length-1];
        assertNotEquals(res.availabilityId(), originalId);

    }

    @Test
    void bookAvailability() {
        //Insert new test element to use
        AvailabilityClass testAvailability = new AvailabilityClass(1, "2024-01-01 00:00:00", "2024-01-01 00:00:30", false);
        assertTrue(service.addAvailability(testAvailability));

        //Get all availabilities
        Collection<Availability> availabilities= service.getAvailability();
        Availability res = (Availability) availabilities.toArray()[availabilities.toArray().length-1];
        Integer testAvailabilityId = res.availabilityId();

        //Book new availability
        assertFalse(res.bookedStatus());
        assertTrue(service.bookAvailability(testAvailabilityId));

        //Update availabilities
        availabilities = service.getAvailability();
        res = (Availability) availabilities.toArray()[availabilities.toArray().length-1];
        Availability finalRes = res;
        assertAll("Validation of if appointment was booked and ID matches",
            () ->assertEquals(testAvailabilityId, finalRes.availabilityId()),
                ()->assertTrue(finalRes.bookedStatus()));
    }

    @Test
    void getValidAvailability() {
        Collection<Availability> availabilities = service.getAvailability();
        assertNotNull(availabilities);
    }
}