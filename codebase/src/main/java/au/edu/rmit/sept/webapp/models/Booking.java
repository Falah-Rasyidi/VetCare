package au.edu.rmit.sept.webapp.models;

public record Booking(
        Integer bookingId,
        Integer customerId,
        Integer petId,
        Integer clinicId,
        Integer availabilityId,
        String status
        )
{
        public Integer getBookingId() {return bookingId;}
}
