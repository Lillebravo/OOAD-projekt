package Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerService {

    CustomerRepository customerRepository = new CustomerRepository();

    public void getAllCustomers() throws SQLException {
        ArrayList<Customer> customers = customerRepository.getAll();

        if (customers.isEmpty()) {
            System.out.println("No customers found in the database.");
        } else {
            for (Customer c : customers) {
                System.out.println(c.toString());
            }
        }
    }

    public Customer getCustomerById(int id) throws SQLException {
        try {
            Customer customer = customerRepository.getCustomerById(id);

            if (customer == null) {
                System.out.println("No customer found with ID: " + id);
                return null;
            }

            return customer;
        } catch (SQLException e) {
            System.out.println("Database error while retrieving customer with ID: " + id);
            throw e;
        }
    }

    public void addCustomer(Customer customer) throws SQLException {
        try {
            customerRepository.addCustomer(customer);
            System.out.println("Customer added successfully.");
        } catch (SQLException e) {
            System.out.println("Database error while adding new customer: " + customer.getName());
            throw e;
        }
    }

    public void updateCustomerEmail(int customerId, String newEmail) throws SQLException {
        try {
            // check if customer exists
            Customer customer = customerRepository.getCustomerById(customerId);
            if (customer == null) {
                System.out.println("Cannot update Email. No customer found with ID: " + customerId);
                return;
            }
            customerRepository.updateCustomerEmail(customerId, newEmail);
            System.out.println("Customer email updated successfully to: " + newEmail);
        } catch (SQLException e) {
            System.out.println("Database error while updating customer email with ID: " + customerId);
            throw e;
        }
    }

}
