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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @HystrixCommand(fallbackMethod = "getDefaultProduct", commandKey = "GetProducts")
    private Product getProductFromCache(String id) {
        log.info("Returning cached product");
        return productCache.get(PRODUCT_CACHE_KEY, id);
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

    // ALL PRODUCTS

    @HystrixCommand(fallbackMethod = "getAllProductsFromCache", commandKey = "GetProducts",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value="10000")
            } )
    public List<Product> getAllProducts() {
        log.info("getAllProducts");
        List<Product> products = productServiceClient.getAllProducts();
        this.updateCacheWithMultipleProducts(products);
        return products;
    }

    @HystrixCommand(fallbackMethod = "getDefaultProducts", commandKey = "GetProducts")
    private List<Product> getAllProductsFromCache() {
        log.info("Returning cached all products");
        return new ArrayList<Product>(productCache.entries(PRODUCT_CACHE_KEY).values());
    }

    private List<Product> getDefaultProducts() {
        log.info("Default fallback for all products");
        return new ArrayList<Product>();
    }

    private void updateCacheWithMultipleProducts(List<Product> products) {
        try {
            log.debug("Updating cache with multiple products");
            Map<String, Product> productsMap = new HashMap<String, Product>();
            for (Product p : products) {
                productsMap.put(p.getId(), p);
            }

            productCache.putAll(PRODUCT_CACHE_KEY, productsMap);
        }
        catch (Throwable e) {
            log.error("Cannot update cache", e);
        }
    }
}
