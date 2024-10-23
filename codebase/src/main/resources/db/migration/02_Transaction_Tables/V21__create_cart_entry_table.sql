DROP TABLE IF EXISTS cartEntry;
CREATE TABLE cartEntry
(
    userId         INT          not null,
    prescriptionId INT          not null,
    quantity       INT          not null,
    FOREIGN KEY (userId) REFERENCES userAccount (userId) ON DELETE CASCADE,
    FOREIGN KEY (prescriptionId) REFERENCES prescription (prescriptionId) ON DELETE CASCADE
);