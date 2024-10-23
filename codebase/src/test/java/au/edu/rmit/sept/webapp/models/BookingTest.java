package au.edu.rmit.sept.webapp.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BookingTest {

    @Test
    void constructorTest() {
        Booking booking1 = new Booking(1,1,1,1,1,"available");
        assertEquals(1, booking1.getBookingId());
    }
}
