DROP TABLE IF EXISTS booking;
CREATE TABLE booking
(
    bookingId      INT PRIMARY KEY AUTO_INCREMENT,
    userId         INT          not null,
    petId          INT          not null,
    clinicId       INT          not null,
    contactId      INT          not null,
    availabilityId INT          not null,
    serviceId      INT          not null,
    status         VARCHAR(255) not null,
    FOREIGN KEY (userId) REFERENCES userAccount (userId) ON DELETE CASCADE,
    FOREIGN KEY (petId) REFERENCES petData (petId),
    FOREIGN KEY (clinicId) REFERENCES clinic (clinicId),
    FOREIGN KEY (contactId) REFERENCES contactInformation (contactId),
    FOREIGN KEY (availabilityId) REFERENCES availability (availabilityId),
    FOREIGN KEY (serviceId) REFERENCES service (serviceId)
);