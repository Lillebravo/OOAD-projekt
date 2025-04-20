package Order;

import java.util.Date;

public class Order {
    private int orderId;
    private int customerId;
    private Date orderDate;
    private double totalAmount;

    // For listing orders
    public Order(int orderId, int customerId, Date orderDate, double totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }

    // For creating a new order
    public Order(int customerId) {
        this.customerId = customerId;
        this.orderDate = new Date();
    }

    public int getCustomerId() {
        return customerId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    @Override
    public String toString() {
        return  "-------------------------------------\n" +
                "Order ID: " + orderId + "\n" +
                "Customer ID: " + customerId + "\n" +
                "Order Date: " + orderDate + "\n" +
                "Total Amount: $" + String.format("%.2f", totalAmount) + "\n" +
                "-------------------------------------";
    }
}