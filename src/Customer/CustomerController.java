package Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerController {

    CustomerService customerService = new CustomerService();

    public void runMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Hämta alla kunder");
        System.out.println("2. Hämta en kund efter id");
        System.out.println("3. Lägg till kund");
        String select = scanner.nextLine();
        switch (select) {
            case "1":
                ArrayList<Customer> customers = customerService.getAllCustomers();
                for(Customer c : customers){
                    System.out.println("KundId: " + c.getCustomerId());
                    System.out.println("Namn: " + c.getName());
                    System.out.println("Email: " + c.getEmail());
                }
            case "2":
                System.out.println("Ange id:");
                int id = scanner.nextInt();
                Customer customer = customerService.getCustomerById(id);
                System.out.println(customer.getName());
            case "3":
                customerService.addCustomer("Exempelnamn", "tele", "mejl", "Hemma", "hemligt");
            case "4":
                System.out.println("Ange email:");
                String email = scanner.nextLine();
                System.out.println("Ange id:");
                int customerId = scanner.nextInt();
                customerService.updateCustomerEmail(email, customerId);
        }

    }

}
