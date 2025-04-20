package Order;

import Product.*;
import Utillities.Validation;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

public class OrderController {

    private OrderService orderService = new OrderService();
    private ProductService productService = new ProductService();

    public void runMenu() throws SQLException {
        boolean orderMenu = true;
        Scanner scanner = new Scanner(System.in);

        while (orderMenu) {
            System.out.println("//-- Order Menu --\\");
            System.out.println("1: View order history for a customer");
            System.out.println("2: Place a new order");
            System.out.println("3: View order details");
            System.out.println("0: Main Menu");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    int customerId = Validation.getValidInt(scanner, "Enter customer ID:", 1,
                            "Customer ID must be 1 or higher.");
                    if (customerId == -1) continue;

                    orderService.getOrderHistoryForCustomer(customerId);
                }

                case "2" -> {
                    int customerId = Validation.getValidInt(scanner, "Enter customer ID:", 1,
                            "Customer ID must be 1 or higher.");
                    if (customerId == -1) continue;

                    HashMap<Integer, Integer> productQuantities = new HashMap<>(); //
                    boolean addingProducts = true;

                    while (addingProducts) {
                        // Displays all available products
                        System.out.println("Available products:");
                        productService.getAllProducts();

                        System.out.println("0: Finish order (if products added) or cancel order");
                        int productId = Validation.getValidInt(scanner, "Enter product ID:", 0,
                                "Product ID must be 0 or higher.");

                        if (productId == -1) {
                            System.out.println("Returning to Order Menu.");
                            addingProducts = false;
                            break;
                        }

                        if (productId == 0) {
                            addingProducts = false;
                            continue;
                        }

                        // Verify product exists
                        Product product = productService.getProductById(productId);
                        if (product == null) {
                            continue;
                        }

                        System.out.println(product.toString()); // Display product selected if it exists
                        int quantity = Validation.getValidInt(scanner, "Enter quantity:", 1,
                                "Quantity must be 1 or higher.");
                        if (quantity == -1) continue;

                        productQuantities.put(productId, quantity); // add key value pairs productId and quantity to hashmap
                        System.out.println("Product added to order. Add another product?");
                    }

                    // If user broke out of the loop with no products
                    if (productQuantities.isEmpty()) {
                        System.out.println("Order cancelled - no products selected.");
                        continue;
                    }

                    // If products in order after breaking out of loop create order with said products
                    int orderId = orderService.createOrder(customerId, productQuantities);
                    if (orderId > 0) {
                        orderService.viewOrderDetails(orderId);
                    }
                }

                case "3" -> {
                    int orderId = Validation.getValidInt(scanner, "Enter order ID:", 1,
                            "Order ID must be 1 or higher.");
                    if (orderId == -1) continue;

                    orderService.viewOrderDetails(orderId);
                }

                case "0" -> {
                    orderMenu = false;
                    System.out.println("Going back to Main Menu.");
                }

                default -> System.out.println("Invalid choice, you can only choose menu options!");
            }
        }
    }
}