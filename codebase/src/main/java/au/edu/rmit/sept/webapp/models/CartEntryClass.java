package au.edu.rmit.sept.webapp.models;

public class CartEntryClass {
    private Integer userId;
    private Integer prescriptionId;
    private Integer quantity;

    private String medicineName;
    private String description;
    private String dosage;
    private String instructions;
    private double price;

    public CartEntryClass() {
        this.userId = null;
        this.prescriptionId = null;
        this.quantity = null;
    }

    public CartEntryClass(Integer userId, Integer prescriptionId, Integer quantity) {
        this.userId = userId;
        this.prescriptionId = prescriptionId;
        this.quantity = quantity;
    }

    public CartEntryClass(Integer userId, Integer prescriptionId, String medicineName, String description, String dosage, String instructions, double price, Integer quantity) {
        this.userId = userId;
        this.prescriptionId = prescriptionId;
        this.medicineName = medicineName;
        this.description = description;
        this.dosage = dosage;
        this.instructions = instructions;
        this.price = price;
        this.quantity = quantity;
    }

    public String toInsertSQL(){
        return("INSERT INTO cartEntry" +
                "(userId," +
                "prescriptionId," +
                "quantity)" +
                "VALUES(" +
                this.toString()+")");
    }
    public String toString(){
        return(String.format(
                "%s, %s, %s",
                this.userId,
                this.prescriptionId,
                this.quantity
        ));
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Integer prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    public String getMedicineName() {
        return medicineName;
    }

    public String getDescription() {
        return description;
    }

    public String getDosage() {
        return dosage;
    }

    public String getInstructions() {
        return instructions;
    }

    public double getPrice() {
        return price;
    }
}