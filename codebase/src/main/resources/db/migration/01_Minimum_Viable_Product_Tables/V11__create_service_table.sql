DROP TABLE IF EXISTS service;
CREATE TABLE service(
    serviceId INT PRIMARY KEY AUTO_INCREMENT,
    clinicId INT not null,
    serviceName VARCHAR(255) not null,
    serviceDescription VARCHAR(2000) not null,
    price FLOAT not null,
    FOREIGN KEY (clinicId) REFERENCES clinic(clinicId)
);