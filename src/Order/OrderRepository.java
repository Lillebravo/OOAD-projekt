package Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderRepository {

    public static final String URL = "jdbc:sqlite:webbutiken.db";

    // 1
    public ArrayList<Order> getOrdersByCustomerId(int customerId) throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();
        String sql = "SELECT o.order_id, o.customer_id, o.order_date, " +
                "SUM(op.quantity * op.unit_price) as total_amount " +
                "FROM orders o " +
                "JOIN orders_products op ON o.order_id = op.order_id " +
                "WHERE o.customer_id = ? " +
                "GROUP BY o.order_id";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("order_id"),
                        rs.getInt("customer_id"),
                        new Date(rs.getTimestamp("order_date").getTime()),
                        rs.getDouble("total_amount")
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving order history: " + e.getMessage());
            throw e;
        }
        return orders;
    }

    // 2
    public int createOrder(Order order) throws SQLException {
        String sql = "INSERT INTO orders (customer_id, order_date) VALUES (?, ?)";


        // Using Return Generated Keys to get the order id from database after order has been inserted
        // as it´s needed for adding products to that specific order
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, order.getCustomerId());
            pstmt.setTimestamp(2, new Timestamp(order.getOrderDate().getTime()));
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Retrieving order_id and returning it
            } else {
                throw new SQLException("Creating order failed, no ID obtained.");
            }
        } catch (SQLException e) {
            System.out.println("Error while creating order: " + e.getMessage());
            throw e;
        }
    }

    public void addProductToOrder(int orderId, int productId, int quantity, double unitPrice) throws SQLException {
        String sql = "INSERT INTO orders_products (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            pstmt.setInt(2, productId);
            pstmt.setInt(3, quantity);
            pstmt.setDouble(4, unitPrice);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error while adding product to order: " + e.getMessage());
            throw e;
        }
    }

    public double calculateOrderTotal(int orderId) throws SQLException {
        String sql = "SELECT SUM(quantity * unit_price) as total " +
                "FROM orders_products " +
                "WHERE order_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("total");
            }
            return 0.0;
        } catch (SQLException e) {
            System.out.println("Error while calculating order total: " + e.getMessage());
            throw e;
        }
    }

    // 3
    public Map<String, Object[]> getOrderProducts(int orderId) throws SQLException {
        Map<String, Object[]> orderProducts = new HashMap<>();
        String sql = "SELECT op.product_id, p.name, op.unit_price, op.quantity " +
                "FROM orders_products op " +
                "JOIN products p ON op.product_id = p.product_id " +
                "WHERE op.order_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String productName = rs.getString("name");
                double unitPrice = rs.getDouble("unit_price");
                int quantity = rs.getInt("quantity");
                int productId = rs.getInt("product_id");

                orderProducts.put(productName, new Object[]{productId, unitPrice, quantity});
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving order products: " + e.getMessage());
            throw e;
        }
        return orderProducts;
    }
}