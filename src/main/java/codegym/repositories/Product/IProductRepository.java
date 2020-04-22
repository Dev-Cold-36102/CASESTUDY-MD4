package codegym.repositories.Product;

import com.codegym.model.Product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IProductRepository extends PagingAndSortingRepository<Product, Long> {
    Page<Product> findAllByNameStartsWith(String startWithText,Pageable pageable);
}
