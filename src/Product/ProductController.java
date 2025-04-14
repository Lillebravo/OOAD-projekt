package Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductController {

    ProductService productService = new ProductService();

    public void runMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Hämta alla produkter");
        System.out.println("2. Hämta en produkt");
        String select = scanner.nextLine();
        switch (select) {
            case "1":
                ArrayList<Product> products = productService.getAllProducts();
                for(Product p : products){
                    System.out.println("Produktnamn: " + p.getName());
                    System.out.println("Pris: " + p.getPrice());
                    System.out.println("Antal: " + p.getQuantity());
                }
            case "2":
                System.out.println("Ange id:");
                int id = scanner.nextInt();
                Product product = productService.getProductById(id);
                System.out.println(product.toString());
            case "3":
                System.out.println("Ange kategori: ");
                String categoryName = scanner.nextLine();
                productService.getProductsByCategoryName(categoryName);
        }

    }

}
