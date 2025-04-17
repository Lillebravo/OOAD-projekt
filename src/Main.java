import Customer.*;
import Product.ProductController;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        CustomerRepository customerRepository = new CustomerRepository();
        ProductController productController = new ProductController();
        Scanner scanner = new Scanner(System.in);
        boolean program = true;

        while (program) {
            System.out.println("//-- Main Menu --\\");
            System.out.println("1: Customer Menu");
            System.out.println("2: Product Menu");
            System.out.println("3: Orders Menu");
            System.out.println("0: Turn off");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> System.out.println("customers");
                case "2" -> productController.runMenu();
                case "3" -> System.out.println("orders");
                case "0" -> {
                    program = false;
                    System.out.println("Program is shutting down.");
                }
                default -> System.out.println("Invalid choice, you can only choose menu options!");

            }
        }

    }
}