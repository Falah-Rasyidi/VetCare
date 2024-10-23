package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.Booking;
import au.edu.rmit.sept.webapp.models.BookingClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookingServiceImplTest {
    @Autowired
    private BookingService service;
    @Test
    void getBookings() {
        Collection<Booking> res = service.getBookings();
        assertNotNull(res);
    }

    @Test
    void addBooking() {
        Collection<Booking> res = service.getBookings();
        Integer initAmount = res.size();
        service.addBooking(new BookingClass(1,1,1,1,"Booked"));
        res = service.getBookings();
        assertEquals(initAmount+1, res.size());
    }
}