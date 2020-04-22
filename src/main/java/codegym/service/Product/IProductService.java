package codegym.service.Product;

import com.codegym.model.Product.Product;
import org.springframework.data.domain.Page;

public interface IProductService {
    Page<Product> getAllProduct(String startWithText,int size, int page);
    Product findProductById(Long id);

    void addProduct(Product product);
    void editProduct(Product product);
    void removeProduct(Product product);
}
