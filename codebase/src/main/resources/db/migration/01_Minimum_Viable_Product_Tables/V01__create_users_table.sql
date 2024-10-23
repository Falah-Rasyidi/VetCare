DROP TABLE IF EXISTS userAccount;
CREATE TABLE userAccount(
    userId INT PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(255) not null,
    lastName VARCHAR(255) not null,
    passwordHash VARCHAR(255) not null,
    phoneNumber VARCHAR(50) not null,
    emailAddress VARCHAR(255) not null,
    permissionLevel ENUM('None','Admin') NOT NULL
);