package ba.unsa.etf.ecommerceproxy.service;

import ba.unsa.etf.ecommerceproxy.feign.ProductServiceClient;
import ba.unsa.etf.ecommerceproxy.model.Product;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ProductService {

    private static final String PRODUCT_CACHE_KEY = "ProductCache";

    Logger log = LogManager.getLogger(ProductService.class);

    @Autowired
    private RedisTemplate<String, Product> redisTemplate;

    @Autowired
    private ProductServiceClient productServiceClient;

    private HashOperations<String, String, Product> productCache;

    @PostConstruct
    public void init() {
        this.productCache = redisTemplate.opsForHash();
    }

//    @HystrixCommand(fallbackMethod = "getProductsFromCache", commandKey = "GetProducts")
////    public List<Product> getProductsByCategory() {
////        List<Product> products = productServiceClient.getAllProducts();
////        this.updateCache(products);
////        return products;
////    }

    @HystrixCommand(fallbackMethod = "getProductFromCache", commandKey = "GetProducts",
    commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value="10000")
    } )
    public Product getProductById(String id) {
        log.info("getProductsById");
        Product product = productServiceClient.getProductById(id);
        this.updateCache(product);
        return product;
    }


    @HystrixCommand(fallbackMethod = "getDefaultProducts", commandKey = "GetProducts")
    private Product getProductFromCache(String id) {
        log.info("Returning cached data");
        return productCache.get(PRODUCT_CACHE_KEY, id);
    }

    private Product getDefaultProducts(String id) {
        log.info("Default fallback");
        return new Product();
    }

    private void updateCache(Product product) {
        try {
            log.debug("Updating cache...");
            productCache.put(PRODUCT_CACHE_KEY, product.getId(), product);
        }
        catch (Throwable e) {
            log.error("Cannot update cache", e);
        }
    }
}
