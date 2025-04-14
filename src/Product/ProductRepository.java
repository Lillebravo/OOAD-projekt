package Product;

import java.sql.*;
import java.util.ArrayList;

public class ProductRepository {

    public static final String URL = "jdbc:sqlite:webbutiken.db";

    public ArrayList<Product> getAll() throws SQLException {

        ArrayList<Product> products = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {

            while (rs.next()) {
                Product product1 = new Product("Hej", 10.10, 50);
                Product product = new Product(rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity"));
                products.add(product);
            }
        }
        return products;
    }

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
        }
    }

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
            if (!rs.next()) {
                return null;
            }
            while (rs.next()) {
                Product product = new Product(rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity"));
                products.add(product);
            }
            return products;
        }
    }

}
