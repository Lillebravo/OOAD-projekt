package Customer;

import Utillities.Validation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerController {

    CustomerService customerService = new CustomerService();

    public void runMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean customerMenu = true;

        while (customerMenu) {
            System.out.println("//-- Customer Menu --\\");
            System.out.println("1. Get all customers");
            System.out.println("2. Search for customer by ID");
            System.out.println("3. Add new customer");
            System.out.println("4. Update Customer Email");
            System.out.println("0. Main Menu");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> customerService.getAllCustomers();

                case "2" -> {
                    int id = Validation.getValidInt(scanner, "Input ID:", 1, "Customer ID must be 1 or higher.");
                    if (id == -1) continue;

                    Customer customer = customerService.getCustomerById(id);
                    if (customer != null) {
                        System.out.println(customer.toString());
                    }
                }

                case "3" -> {
                    String name = Validation.getValidString(scanner, "Name:");
                    if (name == null) continue;
                    String email = Validation.getValidString(scanner, "Email:");
                    if (email == null) continue;
                    String phone = Validation.getValidString(scanner, "Phone nr:");
                    if (phone == null) continue;
                    String address = Validation.getValidString(scanner, "Address:");
                    if (address == null) continue;
                    String password = Validation.getValidString(scanner, "Password:");
                    if (password == null) continue;
                    Customer newCustomer = new Customer(name, phone, email, address, password);
                    customerService.addCustomer(newCustomer);
                }

                case "4" -> {
                    int customerId = Validation.getValidInt(scanner, "Input ID:", 1, "Customer ID must be 1 or higher.");
                    if (customerId == -1) continue;
                    String email = Validation.getValidString(scanner, "Email:");
                    if (email == null) continue;
                    customerService.updateCustomerEmail(customerId, email);
                }

                case "0" -> {
                    customerMenu = false;
                    System.out.println("Going back to Main Menu.");
                }

                default -> System.out.println("Invalid choice, you can only choose menu options!");
            }
        }
    }

}
