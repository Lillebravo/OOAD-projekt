package Order;

import Customer.Customer;
import Customer.CustomerRepository;
import Product.Product;
import Product.ProductService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderService {

    private OrderRepository orderRepository = new OrderRepository();
    private ProductService productService = new ProductService();
    private CustomerRepository customerRepository = new CustomerRepository();

    // 1
    public void getOrderHistoryForCustomer(int customerId) throws SQLException {
        try {
            // Check if customer exists
            Customer customer = customerRepository.getCustomerById(customerId);
            if (customer == null) {
                System.out.println("No customer found with ID: " + customerId);
                return;
            }

            String customerName = customer.getName();
            ArrayList<Order> orders = orderRepository.getOrdersByCustomerId(customerId);

            if (orders.isEmpty()) {
                System.out.println("No order history found for customer: " + customerName + " (ID: " + customerId + ")");
            } else {
                System.out.println("Order history for customer: " + customerName + " (ID: " + customerId + ")");
                for (Order order : orders) {
                    System.out.println(order.toString());
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error while retrieving order history: " + e.getMessage());
            throw e;
        }
    }

    // 2
    public int createOrder(int customerId, HashMap<Integer, Integer> productQuantities) throws SQLException {
        try {
            // Check if customer exists
            Customer customer = customerRepository.getCustomerById(customerId);
            if (customer == null) {
                System.out.println("No customer found with ID: " + customerId);
                return -1;
            }

            // Validate all products exist and have sufficient stock
            // The .entrySet() method returns a collection of all key-value pairs in the HashMap as Map.Entry objects.
            // Each Map.Entry represents one product and its ordered quantity.
            for (Map.Entry<Integer, Integer> entry : productQuantities.entrySet()) {
                int productId = entry.getKey(); // Gets product id
                int quantity = entry.getValue(); // Gets quantity ordered for that corresponding product id

                // Check if product exists
                Product product = productService.getProductById(productId);
                if (product == null) {
                    System.out.println("Product with ID " + productId + " does not exist.");
                    return -1;
                }

                // Check if product has sufficient stock
                if (product.getQuantity() < quantity) {
                    System.out.println("Insufficient stock for product: " + product.getName());
                    System.out.println("Available: " + product.getQuantity() + ", Requested: " + quantity);
                    return -1;
                }
            }

            // Create the order
            Order order = new Order(customerId);
            int orderId = orderRepository.createOrder(order);

            // Add all products to the order
            for (Map.Entry<Integer, Integer> entry : productQuantities.entrySet()) {
                int productId = entry.getKey();
                int quantity = entry.getValue();

                Product product = productService.getProductById(productId);
                double unitPrice = product.getPrice();

                orderRepository.addProductToOrder(orderId, productId, quantity, unitPrice);

                // Update product quantity (decrease stock quantity)
                int newQuantity = product.getQuantity() - quantity;
                productService.updateProductQuantity(productId, newQuantity);
            }

            double total = orderRepository.calculateOrderTotal(orderId);
            System.out.println("Order created successfully with ID: " + orderId);
            System.out.println("Total order amount: $" + String.format("%.2f", total)); // Print total with 2 decimals

            return orderId;
        } catch (SQLException e) {
            System.out.println("Database error while creating order: " + e.getMessage());
            throw e;
        }
    }

    // 3
    public void viewOrderDetails(int orderId) throws SQLException {
        try {
            Map<String, Object[]> orderProducts = orderRepository.getOrderProducts(orderId);

            if (orderProducts.isEmpty()) {
                System.out.println("No products found for order ID: " + orderId);
                return;
            }

            double total = 0.0;
            System.out.println("======= Order Details (ID: " + orderId + ") =======");

            for (Map.Entry<String, Object[]> entry : orderProducts.entrySet()) {
                String productName = entry.getKey(); // Key value is product name
                Object[] details = entry.getValue(); // Each entry in object array is a detail for product, [0] = productId
                double unitPrice = (double) details[1]; // [1] = unit price
                int quantity = (int) details[2]; // [2] = quantity
                double subtotal = unitPrice * quantity;

                // Printing product
                System.out.println("-------------------------------------");
                System.out.println("Product: " + productName);
                System.out.println("Price: $" + String.format("%.2f", unitPrice));
                System.out.println("Quantity: " + quantity);
                System.out.println("Subtotal: $" + String.format("%.2f", subtotal));

                total += subtotal;
            }

            // Printing total
            System.out.println("-------------------------------------");
            System.out.println("Total Order Amount: $" + String.format("%.2f", total));
            System.out.println("-------------------------------------");
        } catch (SQLException e) {
            System.out.println("Database error while retrieving order details: " + e.getMessage());
            throw e;
        }
    }
}