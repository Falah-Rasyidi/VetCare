package au.edu.rmit.sept.webapp.models;

public class BookingClass
{
        private Integer bookingId;
        private Integer customerId;
        private Integer clinicId;
        private Integer petId;
        private Integer availabilityId;
        private String status;

        // New fields for DTO
        private String dateTimeStart;
        private String dateTimeEnd;
        private String petName;
        private String petSpecies;
        private String clinicName;

        // No-argument constructor
        public BookingClass() {
        }

        // Constructor for general bookings
        public BookingClass(Integer customerId, Integer clinicId, Integer petId, Integer availabilityId, String status) {
                this.customerId = customerId;
                this.clinicId = clinicId;
                this.petId = petId;
                this.availabilityId = availabilityId;
                this.status = status;
        }

        // Expanded constructor for extra booking information
        public BookingClass(Integer bookingId, String clinicName, Integer clinicId, Integer availabilityId, String dateTimeStart, String dateTimeEnd, String petName, String petSpecies) {
                this.bookingId = bookingId;
                this.clinicName = clinicName;
                this.clinicId = clinicId;
                this.dateTimeStart = dateTimeStart;
                this.dateTimeEnd = dateTimeEnd;
                this.petName = petName;
                this.petSpecies = petSpecies;
                this.availabilityId = availabilityId;

        }

        // Getters and setters for all fields
        public Integer getBookingId() { return bookingId; }
        public void setBookingId(Integer bookingId) { this.bookingId = bookingId; }

        public Integer getCustomerId() { return customerId; }
        public void setCustomerId(Integer customerId) { this.customerId = customerId; }

        public Integer getClinicId() { return clinicId; }
        public void setClinicId(Integer clinicId) { this.clinicId = clinicId; }

        public Integer getPetId() { return petId; }
        public void setPetId(Integer petId) { this.petId = petId; }

        public Integer getAvailabilityId() { return availabilityId; }
        public void setAvailabilityId(Integer availabilityId) { this.availabilityId = availabilityId; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String toInsertSQL(){
                return("INSERT INTO booking" +
                        "(userId," +
                        "petId," +
                        "clinicId," +
                        "availabilityId," +
                        "status," +
                        "contactId," +
                        "serviceId)" +
                        "VALUES(" +
                        this.toString()+",1,1)");
        }
        public String toString(){
                return(String.format(
                        "'%s', '%s', '%s', '%s', '%s'",
                        this.customerId,
                        this.petId,
                        this.clinicId,
                        this.availabilityId,
                        this.status
                ));
        }

        // DTO getters and setters for the additional fields
        public String getDateTimeStart() { return dateTimeStart; }
        public void setDateTimeStart(String dateTimeStart) { this.dateTimeStart = dateTimeStart; }

        public String getDateTimeEnd() { return dateTimeEnd; }
        public void setDateTimeEnd(String dateTimeEnd) { this.dateTimeEnd = dateTimeEnd; }

        public String getClinicName() { return clinicName; }
        public void setClinicName(String clinicName) { this.clinicName = clinicName; }

        public String getPetName() { return petName; }
        public void setPetName(String petName) { this.petName = petName; }

        public String getPetSpecies() { return petSpecies; }
        public void setPetSpecies(String petSpecies) { this.petSpecies = petSpecies; }
}