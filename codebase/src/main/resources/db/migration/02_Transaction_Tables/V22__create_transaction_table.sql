DROP TABLE IF EXISTS transactions;
CREATE TABLE transactions
(
    transactionId INT PRIMARY KEY AUTO_INCREMENT,
    userId INT NOT NULL,
    status ENUM('Processing','Shipped','Completed') not null,
    dateSubmitted DATE,
    timeSubmitted TIME,
    FOREIGN KEY (userId) REFERENCES userAccount (userId) ON DELETE CASCADE
);