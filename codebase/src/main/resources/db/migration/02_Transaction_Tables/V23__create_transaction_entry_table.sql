DROP TABLE IF EXISTS transactionEntry;
CREATE TABLE transactionEntry
(
    transactionId INT NOT NULL,
    prescriptionId INT NOT NULL,
    quantity       INT NOT NULL,
    purchasePrice FLOAT NOT NULL,
    FOREIGN KEY (transactionId) REFERENCES transactions (transactionId) ON DELETE CASCADE,
    FOREIGN KEY (prescriptionId) REFERENCES prescription (prescriptionId) ON DELETE CASCADE
);