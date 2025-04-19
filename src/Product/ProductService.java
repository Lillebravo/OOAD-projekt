package Product;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductService {

    ProductRepository productRepository = new ProductRepository();

    // 1
    public void getAllProducts() throws SQLException {
        ArrayList<Product> products = productRepository.getAll();

        if (products.isEmpty()) {
            System.out.println("No products found in the database.");
        } else {
            for (Product p : products) {
                System.out.println(p.toString());
            }
        }
    }

    // 2
    public void getProductsByName(String name) throws SQLException {
        ArrayList<Product> products = productRepository.getProductsByName(name);
        if (products.isEmpty()) {
            System.out.println("No products found with name: " + name);
        } else {
            for (Product p : products) {
                System.out.println(p.toString());
            }
        }
    }

    // 3
    public Product getProductById(int id) throws SQLException {
        try {
            Product product = productRepository.getProductById(id);
            if (product == null) {
                System.out.println("No product found with ID: " + id);
                return null;
            }

            return product;

        } catch (SQLException e) {
            System.out.println("Database error while retrieving product with ID: " + id);
            throw e;
        }
    }

    // 4
    public void getProductsByCategoryName(String categoryName) throws SQLException {
        try {
            ArrayList<Product> products = productRepository.getProductsByCategoryName(categoryName);
            if (products.isEmpty()) {
                System.out.println("No products found in category: " + categoryName);
            } else {
                for (Product p : products) {
                    System.out.println(p.toString());
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error while retrieving products from category: " + categoryName);
            throw e;
        }
    }

    // 5
    public void updateProductPrice(int productId, double newPrice) throws SQLException {
        try {
            // Check if the product exists
            Product product = productRepository.getProductById(productId);
            if (product == null) {
                System.out.println("Cannot update price. No product found with ID: " + productId);
                return;
            }

            productRepository.updateProductPrice(productId, newPrice);
            System.out.println("Product price updated successfully.");
        } catch (SQLException e) {
            System.out.println("Database error while updating product price for ID: " + productId);
            throw e;
        }
    }

    // 6
    public void updateProductQuantity(int productId, int newQuantity) throws SQLException {
        try {
            // Check if the product exists
            Product product = productRepository.getProductById(productId);
            if (product == null) {
                System.out.println("Cannot update quantity. No product found with ID: " + productId);
                return;
            }

            productRepository.updateProductQuantity(productId, newQuantity);
            System.out.println("Product quantity updated successfully.");
        } catch (SQLException e) {
            System.out.println("Database error while updating product quantity for ID: " + productId);
            throw e;
        }
    }

    // 7
    public void addProduct(Product product) throws SQLException {
        try {
            productRepository.addProduct(product);
            System.out.println("Product added successfully.");
        } catch (SQLException e) {
            System.out.println("Database error while adding new product: " + product.getName());
            throw e;
        }
    }
}