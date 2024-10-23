DROP TABLE IF EXISTS contactInformation;
CREATE TABLE contactInformation(
    contactId INT PRIMARY KEY AUTO_INCREMENT,
    clinicId INT not null,
    jobPosition VARCHAR(255) not null,
    contactName VARCHAR(255) not null,
    contactNumber VARCHAR(255) not null,
    contactEmail VARCHAR(255) not null,
    FOREIGN KEY (clinicId) REFERENCES clinic(clinicId)
);