import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerController {

    CustomerService customerService = new CustomerService();
    CustomerRepository customerRepository = new CustomerRepository();

    public void runMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Hämta alla kunder");
        System.out.println("2. Hämta en kund efter id");
        String select = scanner.nextLine();
        switch (select) {
            case "1":
                ArrayList<Customer> customers = customerService.getAllCustomers();
                for(Customer c : customers){
                    System.out.println("KundId: " + c.getCustomerId());
                    System.out.println("Namn: " + c.getName());
                }
            case "2":
                System.out.println("Ange id:");
                int id = scanner.nextInt();
                Customer customer = customerRepository.getCustomerById(id);
                System.out.println(customer.getName());
        }

    }

}
