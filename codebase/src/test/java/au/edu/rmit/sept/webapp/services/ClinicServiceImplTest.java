package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.Clinic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClinicServiceImplTest {
    @Autowired
    private ClinicService service;
    @Test
    void getClinics() {
        Collection<Clinic> clinics = service.getClinics();
        assertNotNull(clinics);
    }

    @Test
    void getByAvailabilityId() {
        Clinic res = service.getByAvailabilityId(1);
        assertNotNull(res);
    }
}