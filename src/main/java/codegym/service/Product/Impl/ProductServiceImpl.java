package codegym.service.Product.Impl;

import com.codegym.model.Product.Product;
import com.codegym.repositories.Product.IProductRepository;
import com.codegym.service.Product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class ProductServiceImpl implements IProductService {
    @Autowired
    IProductRepository productRepository;

    @Override
    public Page<Product> getAllProduct(String startWithText, int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = null;
        if (startWithText.isEmpty()) {
            products = this.productRepository.findAll(pageable);
        } else {
            products = this.productRepository.findAllByNameStartsWith(startWithText, pageable);
        }
        return products;
    }

    @Override
    public Product findProductById(Long id) {
        Optional<Product> productOptional = this.productRepository.findById(id);
        if (productOptional.isPresent()) {
            return productOptional.get();
        } else {
            throw new RuntimeException("Không tìm thấy san pham  có id = " + id);
        }
    }

    @Override
    public void addProduct(Product product) {
        this.productRepository.save(product);
    }

    @Override
    public void editProduct(Product product) {
        this.productRepository.save(product);
    }

    @Override
    public void removeProduct(Product product) {
        this.productRepository.delete(product);
    }

}
