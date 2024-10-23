DROP TABLE IF EXISTS availability;
CREATE TABLE availability(
    availabilityId INT PRIMARY KEY AUTO_INCREMENT,
    clinicId INT not null,
    dateTimeStart TIMESTAMP not null,
    dateTimeEnd TIMESTAMP not null,
    bookedStatus BOOLEAN not null,
    FOREIGN KEY (clinicId) REFERENCES clinic(clinicId)
);