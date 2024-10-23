package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.models.Prescription;
import au.edu.rmit.sept.webapp.repositories.PrescriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class PrescriptionServiceImplTest {

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @InjectMocks
    private PrescriptionServiceImpl prescriptionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPrescriptions() {
        // Arrange: Setup mock data
        List<Prescription> mockPrescriptions = Arrays.asList(
                new Prescription(1, "Medicine A", "Desc A", "Dosage A", "Instructions A", 10.5),
                new Prescription(2, "Medicine B", "Desc B", "Dosage B", "Instructions B", 15.0)
        );
        when(prescriptionRepository.getAll()).thenReturn(mockPrescriptions);

        // Act: Call the method being tested
        Collection<Prescription> prescriptions = prescriptionService.getAllPrescriptions();

        // Assert: Check the results
        assertEquals(2, prescriptions.size());
        verify(prescriptionRepository).getAll(); // Verify that the repository's getAll method was called
    }

    @Test
    void testGetPrescriptionById() {
        // Arrange: Setup mock data
        Prescription mockPrescription = new Prescription(1, "Medicine A", "Desc A", "Dosage A", "Instructions A", 10.5);
        when(prescriptionRepository.getById(1)).thenReturn(mockPrescription);

        // Act: Call the method being tested
        Prescription prescription = prescriptionService.getPrescriptionById(1);

        // Assert: Check the results
        assertEquals(1, prescription.prescriptionId());
        assertEquals("Medicine A", prescription.medicineName());
        verify(prescriptionRepository).getById(1); // Verify that the repository's getById method was called
    }

    @Test
    void testFindByOwnerId() {
        // Arrange: Setup mock data
        List<Pet> mockPets = Arrays.asList(
                new Pet(1, 101, "Pet1", "Breed1", "Dog", "Male", "Healthy"),
                new Pet(2, 101, "Pet2", "Breed2", "Cat", "Female", "Healthy")
        );
        when(prescriptionRepository.findByOwnerId("101")).thenReturn(mockPets);

        // Act: Call the method being tested
        Collection<Pet> pets = prescriptionService.findByOwnerId("101");

        // Assert: Check the results
        assertEquals(2, pets.size());
        verify(prescriptionRepository).findByOwnerId("101"); // Verify that the repository's findByOwnerId method was called
    }
}
