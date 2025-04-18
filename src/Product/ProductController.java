package Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductController {
    ProductService productService = new ProductService();

    public void runMenu() throws SQLException {
        boolean productMenu = true;
        Scanner scanner = new Scanner(System.in);

        while (productMenu) {
            System.out.println("1: Get all products");
            System.out.println("2: Search product by Name");
            System.out.println("3: Search product by ID");
            System.out.println("4: Search product by Category");
            System.out.println("5: Update product price");
            System.out.println("6: Update product quantity");
            System.out.println("7: Add new product");
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
                    System.out.println("0: <- Go back");
                    System.out.println("Input Name:");

                    String productName = scanner.nextLine();
                    if (productName.equals("0")) break;

                    if (productName.trim().isEmpty()) {
                        System.out.println("Product name cannot be empty.");
                        break;
                    }

                    productService.getProductsByName(productName);
                }
                case "3" -> {
                    System.out.println("0: <- Go back");
                    System.out.println("Input Id:");

                    try {
                        int id = Integer.parseInt(scanner.nextLine());
                        if (id == 0) break;

                        if (id < 1) {
                            System.out.println("Product ID must be 1 or higher.");
                            break;
                        }

                        Product product = productService.getProductById(id);
                        if (product != null) { // checking if product exists before trying to show it
                            System.out.println(product.toString());
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                    }
                }
                case "4" -> {
                    System.out.println("0: <- Go back");
                    System.out.println("Input category:");

                    String categoryName = scanner.nextLine();
                    if (categoryName.equals("0")) break;

                    if (categoryName.trim().isEmpty()) {
                        System.out.println("Category name cannot be empty.");
                        break;
                    }

                    productService.getProductsByCategoryName(categoryName);
                }
                case "5" -> {
                    System.out.println("0: <- Go back");
                    System.out.println("Input ID for product you want to update:");

                    try {
                        int productId = Integer.parseInt(scanner.nextLine());
                        if (productId == 0) break;

                        if (productId < 1) {
                            System.out.println("Product ID must be 1 or higher.");
                            break;
                        }

                        System.out.println("Input new price for the product:");
                        try {
                            double newPrice = Double.parseDouble(scanner.nextLine());

                            if (newPrice <= 0) {
                                System.out.println("Price must be greater than 0.");
                                break;
                            }

                            productService.updateProductPrice(productId, newPrice);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid price.");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid product ID.");
                    }
                }
                case "6" -> {
                    System.out.println("0: <- Go back");
                    System.out.println("Input ID for product you want to update:");

                    try {
                        int productId = Integer.parseInt(scanner.nextLine());
                        if (productId == 0) break;

                        if (productId < 1) {
                            System.out.println("Product ID must be 1 or higher.");
                            break;
                        }

                        System.out.println("Input new quantity for the product:");
                        try {
                            int newQuantity = Integer.parseInt(scanner.nextLine());

                            if (newQuantity <= 0) {
                                System.out.println("Quantity must be greater than 0.");
                                break;
                            }

                            productService.updateProductQuantity(productId, newQuantity);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid quantity.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid product ID.");
                    }
                }
                case "7" -> {
                    System.out.println("0: <- Go back");

                    System.out.println("Name:");
                    String productName = scanner.nextLine();
                    if (productName.equals("0")) break;

                    if (productName.trim().isEmpty()) {
                        System.out.println("Product name cannot be empty.");
                        break;
                    }

                    System.out.println("Description:");
                    String productDesc = scanner.nextLine();
                    if (productDesc.equals("0")) break;

                    if (productDesc.trim().isEmpty()) {
                        System.out.println("Product description cannot be empty.");
                        break;
                    }

                    System.out.println("Price:");
                    double productPrice;
                    try {
                        productPrice = Double.parseDouble(scanner.nextLine());
                        if (productPrice == 0) break;

                        if (productPrice <= 0) {
                            System.out.println("Price must be greater than 0.");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid price.");
                        break;
                    }

                    System.out.println("Quantity:");
                    int productQuantity;
                    try {
                        productQuantity = Integer.parseInt(scanner.nextLine());
                        if (productQuantity == 0) break;

                        if (productQuantity <= 0) {
                            System.out.println("Quantity must be greater than 0.");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid quantity.");
                        break;
                    }

                    System.out.println("Manufacturer ID:");
                    System.out.println("1: Apple\n2: Samsung\n3: Sony\n4: Dell\n5: LG");
                    int manufacId;
                    try {
                        manufacId = Integer.parseInt(scanner.nextLine());
                        if (manufacId == 0) break;

                        if (manufacId < 1 || manufacId > 5) {
                            System.out.println("Manufacturer ID must be a number between 1-5.");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid manufacturer ID.");
                        break;
                    }

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