package Customer;

import java.sql.*;
import java.util.ArrayList;

public class CustomerRepository {

    public static final String URL = "jdbc:sqlite:webbutiken.db";

    public ArrayList<Customer> getAll() throws SQLException {

        ArrayList<Customer> customers = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM customers")) {

            while (rs.next()) {
                Customer customer = new Customer(rs.getInt("customer_id"),
                                                    rs.getString("name"),
                                                    rs.getString("email"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            System.out.println("Error while getting all products: " + e.getMessage());
            return null;
        }
        return customers;
    }

    public Customer getCustomerById(int customerId) throws SQLException {

        String sql = "SELECT * FROM customers WHERE customer_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            return new Customer(customerId, rs.getString("name"), rs.getString("email"));
        } catch (SQLException e) {
            System.out.println("Error while getting customer by ID: " + customerId);
            throw e;
        }
    }

    public void addCustomer(Customer customer) throws SQLException {

        String sql = "INSERT INTO customers (name, email, phone, address, password) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getEmail());
            pstmt.setString(3, customer.getPhone());
            pstmt.setString(4, customer.getAddress());
            pstmt.setString(5, customer.getPassword());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while adding new customer: " + e.getMessage());
        }
    }

    public void updateCustomerEmail(int customerId, String email) throws SQLException {

        String sql = "UPDATE customers\n" +
                " SET email = ?\n" +
                " WHERE customer_id = ?;";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setInt(2, customerId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while updating email: " + e.getMessage());
        }
    }
}
