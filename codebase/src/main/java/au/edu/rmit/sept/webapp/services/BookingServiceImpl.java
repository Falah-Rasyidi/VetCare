package au.edu.rmit.sept.webapp.services;
import au.edu.rmit.sept.webapp.models.*;
import au.edu.rmit.sept.webapp.repositories.BookingRepository;
import au.edu.rmit.sept.webapp.repositories.AvailabilityRepository;
import au.edu.rmit.sept.webapp.repositories.BookingRepository;
import au.edu.rmit.sept.webapp.repositories.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    private BookingRepository repository;

    @Autowired
    public BookingServiceImpl(BookingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<Booking> getBookings() {
        return repository.findAll();
    }

    @Override
    public boolean addBooking(BookingClass bookingClass) {
        boolean returnValue = repository.updateBooking(bookingClass);
        //Return value held in-case there is handling for failed insertion
        return returnValue;
    }

    @Override
    public boolean removeBooking(int availabilityId) {
        return repository.removeBooking(availabilityId);
    }

    @Override
    public Collection<BookingClass> findDetailedBookingsByCustomerId(Integer customerId) {
        return repository.findDetailedBookingsByCustomerId(customerId);
    }

    public boolean rescheduleBooking(int bookingId, int selectedAvailabilityId) {
        return repository.rescheduleBooking(bookingId, selectedAvailabilityId);
    }
}