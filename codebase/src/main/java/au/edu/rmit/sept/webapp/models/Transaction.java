package au.edu.rmit.sept.webapp.models;

public record Transaction(
        Integer transactionId,
        Integer userId,
        String status,
        String dateSubmitted,
        String timeSubmitted
) {
}
