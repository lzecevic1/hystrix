package ba.unsa.etf.productservice.service;

import ba.unsa.etf.productservice.model.DBProduct;
import ba.unsa.etf.productservice.model.Product;
import ba.unsa.etf.productservice.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllByCategory(String category) {
        List<DBProduct> dbProducts = productRepository.findByCategory(category);
        return mapDbProductsToProducts(dbProducts);
    }

    public List<Product> getAllProducts() {
        List<DBProduct> dbProducts = productRepository.findAll();
        return mapDbProductsToProducts(dbProducts);
    }

    public Product getProductById(String id) {
        Product product = new Product();
        product.setId(id);
        product.setName("test");
        product.setCategory("test category");
        product.setPrice(new BigDecimal(40.55));
        return product;
//        return mapDbProductToProduct(productRepository.findById(id));
    }

    private List<Product> mapDbProductsToProducts(List<DBProduct> dbProducts) {
        List<Product> products = new ArrayList<>();
        for (DBProduct dbProduct: dbProducts) {
            Product product = new Product();
            product.setId(dbProduct.getId());
            product.setName(dbProduct.getName());
            product.setCategory(dbProduct.getCategory());
            product.setPrice(dbProduct.getPrice());
            products.add(product);
        }
        return products;
    }

    private Product mapDbProductToProduct(DBProduct dbProduct) {
        Product product = new Product();
        product.setId(dbProduct.getId());
        product.setName(dbProduct.getName());
        product.setCategory(dbProduct.getCategory());
        product.setPrice(dbProduct.getPrice());
        return product;
    }

}
