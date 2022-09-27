package com.stonemason.rediscache.api;

import com.stonemason.rediscache.dao.entity.Product;
import com.stonemason.rediscache.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/product")
@Slf4j
@RestController
@Api(hidden = true, tags = { "Product" })
public class ProductApiImpl implements ProductApi {

    @Autowired
    ProductService productService;

    @Autowired
    CacheManager cacheManager;

    @Override
    public ResponseEntity<Product> createProduct(String name) {
        return ResponseEntity.ok(productService.add(name));
    }

    @Override
    public ResponseEntity<Product> getProduct(int id) {
        log.info("Request has arrived to getProduct by id: " + id);
        return ResponseEntity.ok(productService.get(id));
    }

    @Override
    public ResponseEntity<Product> updateProduct(int id, String name) {
        log.info("Product's name will set as " + name);
        return ResponseEntity.ok(productService.update(id, name));
    }

}
