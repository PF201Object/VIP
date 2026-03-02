package Models;

public class Shipping {
    private int shipId;
    private int transactionId;
    private String courierName;
    private String trackingNumber;
    private String shippingStatus;
    private String shippingAddress;
    private String shippedDate;
    private String estimatedDelivery;

    public Shipping() {}

    public Shipping(int shipId, int transactionId, String trackingNumber, String shippingStatus) {
        this.shipId = shipId;
        this.transactionId = transactionId;
        this.trackingNumber = trackingNumber;
        this.shippingStatus = shippingStatus;
    }

    public int getShipId() { return shipId; }
    public void setShipId(int shipId) { this.shipId = shipId; }

    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }

    public String getCourierName() { return courierName; }
    public void setCourierName(String courierName) { this.courierName = courierName; }

    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }

    public String getShippingStatus() { return shippingStatus; }
    public void setShippingStatus(String shippingStatus) { this.shippingStatus = shippingStatus; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public String getShippedDate() { return shippedDate; }
    public void setShippedDate(String shippedDate) { this.shippedDate = shippedDate; }

    public String getEstimatedDelivery() { return estimatedDelivery; }
    public void setEstimatedDelivery(String estimatedDelivery) { this.estimatedDelivery = estimatedDelivery; }
}