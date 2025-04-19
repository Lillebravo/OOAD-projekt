package Product;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import Utillities.Validation;

public class ProductController {
    ProductService productService = new ProductService();

    public void runMenu() throws SQLException {
        boolean productMenu = true;
        Scanner scanner = new Scanner(System.in);

        while (productMenu) {
            System.out.println("//-- Product Menu --\\");
            System.out.println("1: Get all products");
            System.out.println("2: Search product by Name");
            System.out.println("3: Search product by ID");
            System.out.println("4: Search product by Category");
            System.out.println("5: Update product price");
            System.out.println("6: Update product quantity");
            System.out.println("7: Add new product");
            System.out.println("0: Main Menu");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> productService.getAllProducts();

                case "2" -> {
                    String productName = Validation.getValidString(scanner, "Input Name:");
                    if (productName == null) continue;

                    productService.getProductsByName(productName);
                }

                case "3" -> {
                    int id = Validation.getValidInt(scanner, "Input ID:", 1, "Product ID must be 1 or higher.");
                    if (id == -1) continue;

                    Product product = productService.getProductById(id);
                    if (product != null) {
                        System.out.println(product.toString());
                    }
                }

                case "4" -> {
                    String categoryName = Validation.getValidString(scanner, "Input category:");
                    if (categoryName == null) continue;

                    productService.getProductsByCategoryName(categoryName);
                }

                case "5" -> {
                    int productId = Validation.getValidInt(scanner, "Input ID for product you want to update:", 1,
                            "Product ID must be 1 or higher.");
                    if (productId == -1) continue;

                    double newPrice = Validation.getValidDouble(scanner, "Input new price for the product:", 0.01,
                            "Price must be greater than 0.");
                    if (newPrice == -1.0) continue;

                    productService.updateProductPrice(productId, newPrice);
                }

                case "6" -> {
                    int productId = Validation.getValidInt(scanner, "Input ID for product you want to update:", 1,
                            "Product ID must be 1 or higher.");
                    if (productId == -1) continue;

                    int newQuantity = Validation.getValidInt(scanner, "Input new quantity for the product:", 1,
                            "Quantity must be greater than 0.");
                    if (newQuantity == -1) continue;

                    productService.updateProductQuantity(productId, newQuantity);
                }

                case "7" -> {
                    String productName = Validation.getValidString(scanner, "Name:");
                    if (productName == null) continue;

                    String productDesc = Validation.getValidString(scanner, "Description:");
                    if (productDesc == null) continue;

                    double productPrice = Validation.getValidDouble(scanner, "Price:", 0.01,
                            "Price must be greater than 0.");
                    if (productPrice == -1.0) continue;

                    int productQuantity = Validation.getValidInt(scanner, "Quantity:", 1,
                            "Quantity must be greater than 0.");
                    if (productQuantity == -1) continue;

                    int manufacId = Validation.getValidInt(scanner, "Manufacturer ID:", 1,
                            "Manufacturer ID must be 1 or higher.");
                    if (manufacId == -1) continue;

                    Product newProduct = new Product(productName, productDesc, productPrice, productQuantity, manufacId);
                    productService.addProduct(newProduct);
                }

                case "0" -> {
                    productMenu = false;
                    System.out.println("Going back to Main Menu.");
                }

                default -> System.out.println("Invalid choice, you can only choose menu options!");
            }
        }
    }
}