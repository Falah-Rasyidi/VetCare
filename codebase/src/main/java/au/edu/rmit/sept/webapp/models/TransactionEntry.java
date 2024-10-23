package au.edu.rmit.sept.webapp.models;

public record TransactionEntry(
        Integer transactionId,
        Integer prescriptionId,
        Integer quantity,
        Float purchasePrice
) {
}
