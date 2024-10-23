package au.edu.rmit.sept.webapp.models;

import java.awt.print.Book;

public class AvailabilityClass {
        private Integer clinicID;
        private String dateTimeStart;
        private String dateTimeEnd;
        private Boolean bookedStatus;

        public AvailabilityClass() {
                this.clinicID=0;
                this.dateTimeStart=""; //2024-09-12 12.00.00
                this.dateTimeEnd="";
                this.bookedStatus=false;
        }
        public AvailabilityClass(Integer clinicID, String dateTimeStart, String dateTimeEnd, Boolean bookedStatus) {
                this.clinicID=clinicID;
                this.dateTimeStart=dateTimeStart;
                this.dateTimeEnd=dateTimeEnd;
                this.bookedStatus=bookedStatus;
        }

        public Integer getClinicID() {
                return clinicID;
        }

        public void setClinicID(Integer clinicID) {
                this.clinicID = clinicID;
        }

        public String getDateTimeStart() {
                return dateTimeStart;
        }

        public void setDateTimeStart(String dateTimeStart) {
                this.dateTimeStart = dateTimeStart;
        }

        public String getDateTimeEnd() {
                return dateTimeEnd;
        }

        public void setDateTimeEnd(String dateTimeEnd) {
                this.dateTimeEnd = dateTimeEnd;
        }

        public Boolean getBookedStatus() {
                return bookedStatus;
        }

        public void setBookedStatus(Boolean bookedStatus) {
                this.bookedStatus = bookedStatus;
        }

        public String toInsertSQL(){
                return("INSERT INTO availability " +
                        "(clinicId," +
                        "dateTimeStart," +
                        "dateTimeEnd," +
                        "bookedStatus) " +
                        "VALUES (" +
                        this.toString()+");");
        }

        public String toString(){
                return(String.format(
                        "%s, '%s', '%s', %s",
                        this.clinicID,
                        this.dateTimeStart,
                        this.dateTimeEnd,
                        this.bookedStatus
                ));
        }
}
