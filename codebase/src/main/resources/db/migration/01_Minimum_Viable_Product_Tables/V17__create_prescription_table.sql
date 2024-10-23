DROP TABLE IF EXISTS prescription;
CREATE TABLE prescription(
    prescriptionId INT PRIMARY KEY AUTO_INCREMENT,
    medicineName varchar(255) not null,
    description VARCHAR(255) not null,
    dosage VARCHAR(255) not null,
    instructions VARCHAR(2000) not null,
    price DOUBLE not null
);