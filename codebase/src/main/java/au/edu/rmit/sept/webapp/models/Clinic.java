package au.edu.rmit.sept.webapp.models;

public record Clinic(
        Integer clinicId,
        String clinic,
        String description,
        String address,
        String image) {
    public String formatClinicData(String clinic, String description, String address, String image) {
        String clinicName = clinic == null ? "**Clinic name unavailable**" : clinic;
        String clinicDescription = description == null ? "**Clinic description unavailable**" : description;
        String clinicAddress = address == null ? "**Clinic address unavailable**" : address;
        String clinicImage = image == null ? "**Clinic image unavailable**" : image;

        return String.format("%s (%s)%n%s%s", clinicName, clinicAddress, clinicDescription, clinicImage);
    }
}
