package Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductController {
    ProductService productService = new ProductService();

    public void runMenu() throws SQLException {
        boolean productMenu = true;

        while (productMenu) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("1: Get all products");
            System.out.println("2: Search product by Name");
            System.out.println("3: Search product by ID");
            System.out.println("4: Search product by Category");

            System.out.println("0: Main Menu");
            String select = scanner.nextLine();
            switch (select) {
                case "1" -> {
                    ArrayList<Product> products = productService.getAllProducts();
                    for (Product p : products) {
                        System.out.println("Product Name: " + p.getName());
                        System.out.println("Price: " + p.getPrice());
                        System.out.println("Quantity: " + p.getQuantity());
                    }
                }
                case "2" -> {
                    System.out.println("Input Name:");
                    String productName = scanner.nextLine();
                    productService.getProductsByName(productName);
                }
                case "3" -> {
                    System.out.println("Input Id:");
                    int id = scanner.nextInt();
                    Product product = productService.getProductById(id);
                    System.out.println(product.toString());
                }
                case "4" -> {
                    System.out.println("Input category:");
                    String categoryName = scanner.nextLine();
                    productService.getProductsByCategoryName(categoryName);
                }
                case "0" -> {
                    productMenu = false;
                    System.out.println("Going back to Main Menu.");
                }
            }
        }

    }

}
