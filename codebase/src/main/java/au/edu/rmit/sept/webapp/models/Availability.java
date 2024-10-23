package au.edu.rmit.sept.webapp.models;

public record Availability(
        Integer availabilityId,
        Integer clinicID,
        String dateTimeStart,
        String dateTimeEnd,
        Boolean bookedStatus
        )
{
        public Integer availabilityId() {return availabilityId;}
}
