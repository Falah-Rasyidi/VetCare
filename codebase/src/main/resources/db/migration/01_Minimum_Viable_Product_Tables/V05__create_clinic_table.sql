DROP TABLE IF EXISTS clinic;
CREATE TABLE clinic(
    clinicId INT PRIMARY KEY AUTO_INCREMENT,
    clinicName VARCHAR(255) not null,
    description VARCHAR(2000) not null,
    address VARCHAR(255) not null,
    image VARCHAR(500)
);