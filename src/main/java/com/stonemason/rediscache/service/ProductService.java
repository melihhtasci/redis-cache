package com.stonemason.rediscache.service;

import com.stonemason.rediscache.dao.entity.Product;
import org.springframework.stereotype.Service;


public interface ProductService {

    Product add(String name);

    Product get(int id);

    Product update(int id, String name);

}
