package au.edu.rmit.sept.webapp.models;

public record CartEntry(
        Integer userId,
        Integer prescriptionId,
        Integer quantity
) {
}
