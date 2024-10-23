package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.*;
import java.util.List;

public interface BookingRepository {
    public List<Booking> findAll();
    public Boolean updateBooking(BookingClass user);
    public Boolean removeBooking(int availabilityId);
    public List<BookingClass> findDetailedBookingsByCustomerId(Integer customerId);
    public Boolean rescheduleBooking(int bookingId, int selectedAvailabilityId);
}