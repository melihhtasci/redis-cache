package com.stonemason.rediscache.service;

import com.stonemason.rediscache.dao.entity.Product;
import com.stonemason.rediscache.dao.repo.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product add(String name) {
        return productRepository.save(new Product(0L, name));
    }

    @Override
    @Cacheable(value = "product", key = "#id")
    public Product get(int id) {
        log.info("Product will come from database");
        return productRepository.findById(Long.valueOf(id)).orElseThrow();
    }

    @Override
    public Product update(int id, String name) {
        Product product = productRepository.findById(Long.valueOf(id)).orElseThrow();
        product.setName(name);
        return productRepository.save(product);
    }
}
