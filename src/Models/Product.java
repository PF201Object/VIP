package Models;

public class Product {
    private int watchId;
    private String modelName;
    private String brand;
    private double price;
    private int stockQuantity;
    private String description;
    private String imagePath;

    public Product() {}

    public Product(int watchId, String modelName, String brand, double price) {
        this.watchId = watchId;
        this.modelName = modelName;
        this.brand = brand;
        this.price = price;
    }

    public int getWatchId() { return watchId; }
    public void setWatchId(int watchId) { this.watchId = watchId; }

    public String getModelName() { return modelName; }
    public void setModelName(String modelName) { this.modelName = modelName; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getFullName() { return brand + " " + modelName; }
}