package au.edu.rmit.sept.webapp.models;

public record Product(String productName, String productDescription, int quantity, double price, double specialPrice) {
    public String testName() {
        return productName;
    }
}
