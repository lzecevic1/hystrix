package ba.unsa.etf.productservice.controller;

import ba.unsa.etf.productservice.model.DBProduct;
import ba.unsa.etf.productservice.model.Product;
import ba.unsa.etf.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/all")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

//    @GetMapping(value = "/products", params = "category")
//    public List<Product> getProductsByCategory(@RequestParam String category) {
//        return productService.getAllByCategory(category);
//    }

    @GetMapping(value = "/products/{id}")
    public Product getProductById(@PathVariable(value = "id") String id) {
        return productService.getProductById(id);
    }
}
