import Customer.CustomerController;
import Product.ProductController;
import Order.OrderController;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {

        CustomerController customerController = new CustomerController();
        ProductController productController = new ProductController();
        OrderController orderController = new OrderController();

        Scanner scanner = new Scanner(System.in);
        boolean programRunning = true;

        while (programRunning) {
            System.out.println("//-- Main Menu --\\");
            System.out.println("1: Customer Menu");
            System.out.println("2: Product Menu");
            System.out.println("3: Orders Menu");
            System.out.println("0: Turn off program");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> customerController.runMenu();
                case "2" -> productController.runMenu();
                case "3" -> orderController.runMenu();
                case "0" -> {
                    programRunning = false;
                    System.out.println("Program is shutting down.");
                }
                default -> System.out.println("Invalid choice, you can only choose menu options!");

            }
        }

    }
}