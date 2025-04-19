package Product;

public class Product {

    private String name;
    private String description;
    private double price;
    private int quantity;
    private int manufacturerId;

    // for adding a new product into database
    public Product(String name, String description, double price, int quantity, int manufacturerId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.manufacturerId = manufacturerId;
    }

    // for listing products
    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getDescription() { return description; }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getManufacturerId() { return manufacturerId; }

    @Override
    public String toString() {
        return  "-------------------------------------\n" +
                "Name: " + name + "\n" +
                "Price: $" + String.format("%.2f", price) + "\n" +
                "Quantity: " + quantity + "\n" +
                "-------------------------------------";
    }
}
