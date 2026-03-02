package Models;

public class OrderItem {
    private int itemId;
    private int transactionId;
    private int watchId;
    private int quantity;
    private double priceAtPurchase;

    public OrderItem() {}

    public OrderItem(int itemId, int transactionId, int watchId, int quantity, double priceAtPurchase) {
        this.itemId = itemId;
        this.transactionId = transactionId;
        this.watchId = watchId;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }

    public int getWatchId() { return watchId; }
    public void setWatchId(int watchId) { this.watchId = watchId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPriceAtPurchase() { return priceAtPurchase; }
    public void setPriceAtPurchase(double priceAtPurchase) { this.priceAtPurchase = priceAtPurchase; }

    public double getSubtotal() { return quantity * priceAtPurchase; }
}