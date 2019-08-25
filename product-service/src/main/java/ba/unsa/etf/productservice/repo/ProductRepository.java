package ba.unsa.etf.productservice.repo;

import ba.unsa.etf.productservice.model.DBProduct;
import ba.unsa.etf.productservice.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<DBProduct, Long> {

    List<DBProduct> findByCategory(String category);

    @Override
    List<DBProduct> findAll();

    DBProduct findById(String id);
}
