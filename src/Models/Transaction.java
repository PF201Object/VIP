package Models;

public class Transaction {
    private int transactionId;
    private int customerId;
    private double totalAmount;
    private String orderDate;
    private String paymentMethod;
    private String status;
    private int createdBy;

    public Transaction() {}

    public Transaction(int transactionId, int customerId, double totalAmount, String status) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getOrderDate() { return orderDate; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }
}