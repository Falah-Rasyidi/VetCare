package au.edu.rmit.sept.webapp.services;
import au.edu.rmit.sept.webapp.models.Booking;
import au.edu.rmit.sept.webapp.models.BookingClass;
import au.edu.rmit.sept.webapp.models.Pet;

import java.util.Collection;
import java.util.Optional;

public interface BookingService {
    public Collection<Booking> getBookings();

    public boolean addBooking(BookingClass newBooking);

    public boolean removeBooking(int availabilityId);

    public Collection<BookingClass> findDetailedBookingsByCustomerId(Integer customerId);

    public boolean rescheduleBooking(int bookingId, int selectedAvailabilityId);
}