package au.edu.rmit.sept.webapp.models;

public record Prescription(
        Integer prescriptionId,
        String medicineName,
        String description,
        String dosage,
        String instructions,
        Double price) {

}
