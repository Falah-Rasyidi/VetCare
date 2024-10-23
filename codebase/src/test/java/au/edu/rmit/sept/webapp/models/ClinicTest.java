//package au.edu.rmit.sept.webapp.models;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import org.junit.jupiter.api.Test;
//
//public class ClinicTest {
//    // Test clinic data
//    final int CLINIC_ID = 1;
//    final String CLINIC_NAME = "Happy Tails Veterinary Hospital";
//    final String CLINIC_DESCRIPTION = "Happy Tails offers a wide range of services, including vaccinations, surgical procedures, and pet wellness programs, with a focus on preventive care.";
//    final String CLINIC_ADDRESS = "65 Lewin Street, Gidginburg, Victoria 3666";
//
//    /**
//     * Test clinic formatting when clinic name unavailable
//     * Example:
//     * **Clinic name unavailable** (65 Lewin Street, Gidginburg, Victoria 3666)
//     * Happy Tails offers a wide range of services, including vaccinations, surgical procedures, and pet wellness programs, with a focus on preventive care.
//     */
//    @Test
//    void formatWhenNameNotAvailable() {
//        Clinic clinic = new Clinic(null,null, CLINIC_DESCRIPTION, CLINIC_ADDRESS);
//        final String correctFormat = String.format("%s (%s)%n%s", "**Clinic name unavailable**", CLINIC_ADDRESS, CLINIC_DESCRIPTION);
//        assertEquals(correctFormat, clinic.formatClinicData(null, CLINIC_DESCRIPTION, CLINIC_ADDRESS));
//    }
//
//    /**
//     * Test clinic formatting when clinic description unavailable
//     * Example:
//     * Happy Tails Veterinary Hospital (65 Lewin Street, Gidginburg, Victoria 3666)
//     * **Clinic description unavailable**
//     */
//    @Test
//    void formatWhenDescriptionNotAvailable() {
//        Clinic clinic = new Clinic(null, CLINIC_NAME, null, CLINIC_ADDRESS);
//        final String correctFormat = String.format("%s (%s)%n%s", CLINIC_NAME, CLINIC_ADDRESS, "**Clinic description unavailable**");
//        assertEquals(correctFormat, clinic.formatClinicData(CLINIC_NAME, null, CLINIC_ADDRESS));
//    }
//
//    /**
//     * Test clinic formatting when clinic address unavailable
//     * Example:
//     * Happy Tails Veterinary Hospital (**Clinic address unavailable**)
//     * Happy Tails offers a wide range of services, including vaccinations, surgical procedures, and pet wellness programs, with a focus on preventive care.
//     */
//    @Test
//    void formatWhenAddressNotAvailable() {
//        Clinic clinic = new Clinic(null, CLINIC_NAME, CLINIC_DESCRIPTION, null);
//        final String correctFormat = String.format("%s (%s)%n%s", CLINIC_NAME, "**Clinic address unavailable**", CLINIC_DESCRIPTION);
//        assertEquals(correctFormat, clinic.formatClinicData(CLINIC_NAME, CLINIC_DESCRIPTION, null));
//    }
//}
