package Product;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductService {

    ProductRepository productRepository = new ProductRepository();

    public ArrayList<Product> getAllProducts() throws SQLException {
        return productRepository.getAll();
    }

    public Product getProductById(int id) throws SQLException {
        return productRepository.getProductById(id);
    }

    public void getProductsByCategoryName(String categoryName) throws SQLException {
        ArrayList<Product> products = productRepository.getProductsByCategoryName(categoryName);
        for (Product p : products){
            System.out.println(p.toString());
        }

    }

}
