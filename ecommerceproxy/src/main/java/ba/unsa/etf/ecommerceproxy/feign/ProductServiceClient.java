package ba.unsa.etf.ecommerceproxy.feign;

import ba.unsa.etf.ecommerceproxy.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "products-service")
public interface ProductServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/products/all")
    List<Product> getAllProducts();

    @RequestMapping(method = RequestMethod.GET, value = "/products/{id}")
    Product getProductById(@PathVariable(value = "id") String id);
}
