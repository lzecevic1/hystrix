package ba.unsa.etf.ecommerceproxy.controller;

import ba.unsa.etf.ecommerceproxy.model.Product;
import ba.unsa.etf.ecommerceproxy.service.ProductService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ECommerceController {

    @Autowired
    private ProductService productService;

////    @HystrixCommand(fallbackMethod = "getDefaultProducts")
//    @GetMapping("/products")
//    public List<>Product getProductsByCategory(@RequestParam String category) {
//        return productService.getProductsByCategory(category);
//    }
//
//    public List<Product> getDefaultProducts() {
//        return new ArrayList<>();
//    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }
}
