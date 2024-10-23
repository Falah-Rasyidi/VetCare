DROP TABLE IF EXISTS petData;
CREATE TABLE petData
(
    petId       INT PRIMARY KEY AUTO_INCREMENT,
    userId      INT          not null,
    petName     VARCHAR(255) not null,
    gender      ENUM('Male','Female','N/A') not null,
    species     VARCHAR(255) not null,
    breed       VARCHAR(255) not null,
    dateOfBirth DATE,
    FOREIGN KEY (userId) REFERENCES userAccount (userId) ON DELETE CASCADE
);