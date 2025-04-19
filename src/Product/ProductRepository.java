package Product;

import java.sql.*;
import java.util.ArrayList;

public class ProductRepository {

    public static final String URL = "jdbc:sqlite:webbutiken.db";

    // 1
    public ArrayList<Product> getAll() throws SQLException {

        ArrayList<Product> products = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {

            while (rs.next()) {
                Product product = new Product(rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity"));
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println("Error while getting all products: " + e.getMessage());
            return null;
        }
        return products;
    }

    // 2
    public ArrayList<Product> getProductsByName(String productName) throws SQLException {
        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + productName + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Product product = new Product(rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity"));
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println("Error while searching for products by Name: " + e.getMessage());
        }
        return products;
    }

    // 3
    public Product getProductById(int productId) throws SQLException {

        String sql = "SELECT * FROM products WHERE product_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            return new Product(rs.getString("name"), rs.getDouble("price"), rs.getInt("stock_quantity"));
        } catch (SQLException e) {
            System.out.println("Error while searching for product by ID: " + e.getMessage());
            return null;
        }
    }

    // 4
    public ArrayList<Product> getProductsByCategoryName(String categoryName) throws SQLException {

        ArrayList<Product> products = new ArrayList<>();

        String sql = "SELECT p.*, c.name\n" +
                "  FROM products p\n" +
                "  JOIN products_categories pc ON p.product_id=pc.product_id\n" +
                "  JOIN categories c ON c.category_id=pc.category_id\n" +
                "  WHERE c.name LIKE ?;";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + categoryName + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Product product = new Product(rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity"));
                products.add(product);
            }

        } catch (SQLException e) {
            System.out.println("Error while searching for products by Category: " + e.getMessage());
        }

        return products;
    }

    // 5
    public void updateProductPrice(int productId, double newPrice) throws SQLException {
        String sql = "UPDATE products SET price = ? WHERE product_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, newPrice);
            pstmt.setInt(2, productId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error while updating product price: " + e.getMessage());
        }
    }

    // 6
    public void updateProductQuantity(int productId, int newQuantity) throws SQLException {
        String sql = "UPDATE products SET stock_quantity = ? WHERE product_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, newQuantity);
            pstmt.setInt(2, productId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error while updating product quantity: " + e.getMessage());
        }
    }

    // 7
    public void addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO products (manufacturer_id, name, description, price, stock_quantity) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, product.getManufacturerId());
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getDescription());
            pstmt.setDouble(4, product.getPrice());
            pstmt.setInt(5, product.getQuantity());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error while adding new product: " + e.getMessage());
        }
    }
}
