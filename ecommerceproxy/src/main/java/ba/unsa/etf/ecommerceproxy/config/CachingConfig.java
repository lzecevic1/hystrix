package ba.unsa.etf.ecommerceproxy.config;

import ba.unsa.etf.ecommerceproxy.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author fdanismaz
 * date: 9/20/18 12:08 PM
 */
@EnableCaching
@Configuration
public class CachingConfig {
//
//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;

//    @Bean
//    public LettuceConnectionFactory redisConnectionFactory() {
//
//        return new LettuceConnectionFactory();
//    }
//
//    @Bean
//    public RedisTemplate<String, Product> productRedisTemplate() {
//        RedisTemplate<String, Product> productRedisTemplate = new RedisTemplate<>();
//        productRedisTemplate.setConnectionFactory(redisConnectionFactory());
//        productRedisTemplate.setEnableTransactionSupport(true);
//        return productRedisTemplate;
//    }


    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setUsePool(true);
        return jedisConnectionFactory;
    }

    @Bean
    @Qualifier("productRedisTemplate")
    public RedisTemplate<String, Product> productRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate<String, Product>();
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }

    @Bean
    @Qualifier("productCacheManager")
    public CacheManager productCacheManager() {
        return new RedisCacheManager.RedisCacheManagerBuilder().
    }
}
