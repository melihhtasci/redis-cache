# Redis Cache Demo

This is a demo spring boot project what includes basic usage of redis cache. 

I runned redis on docker by code that below. For more detail visit [here](https://redis.io/docs/stack/get-started/install/docker/).
```
docker pull redis 
docker run --name redis-stack redis
docker exec -it redis-stack redis-cli
```

I used h2, jpa and redis dependencies in this project. You can check pom.xml. For redis, 
i added a configuration on application.yml. Other configurations are at **RedisConfiguration.class**
```
  redis:
    host: localhost
```
You can add ttl or disableCache-for-null-value options on application yml too 
but i added in another configuration file. Maybe we want add more details too.
Configurations for redis below as you can see
```
@Bean
    RedisCacheConfiguration redisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext
                        .SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .serializeKeysWith(RedisSerializationContext
                        .SerializationPair.fromSerializer(new StringRedisSerializer()));
    }
```
Another configuration is enable caching. We have to add @EnableCaching on main class.
```
@SpringBootApplication
@EnableCaching
public class RedisCacheApplication {
	public static void main(String[] args) {
		SpringApplication.run(RedisCacheApplication.class, args);
	}
}
```
I created a basic api what have 4 endpoints and do crud operations as you can see **ProductApi**.
We will use last endpoint to clear cache as you can see.
 
I created an entity that name Product who have just id and name. There is a file 
that i created and named **data.sql** for insert a few data sample in resources.

I created repository and service too. 
```
@Service
public interface ProductService {
    Product add(String name);
    Product get(int id);
    Product update(int id, String name);
}
```
Here we go, trick part has came! just jocking :) We have to add **Cacheable** annotation on method 
that we want to cache the value. I added **@Cacheable(value = "product")**.
by this value, we will store value on redis for example **product::567**
I wrote log for see that will we go into that method or not.
```
@Override
@Cacheable(value = "product")
public Product get(int id) {
    log.info("Product will come from database");
    return productRepository.findById(Long.valueOf(id)).orElseThrow();
}
```
This is get product method on api. 
```
@Override
public ResponseEntity<Product> getProduct(int id) {
    log.info("Request has arrived to getProduct by id: " + id);
    return ResponseEntity.ok(productService.get(id));
}
```
We have two data in data.sql.
In our scenario,
1. Get product which id is 1.
2. See the logs 
```
  Request has arrived to getProduct by id: 1
  Product will come from database
```
3. Get same product which id is 1.
4. See the logs 
```
  Request has arrived to getProduct by id: 1
  Product will come from database
  Request has arrived to getProduct by id: 1
```
By the way you can check on redis cli too. For check: 
```
keys * // gives all keys that redis store
get product::1 // gives stored value that specified cache
```
5. Update same product which id is 1 and get same product.
6. See the logs and result. It changed on db but still come from cache.

That's all.
Thank you.








