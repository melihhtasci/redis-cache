package com.stonemason.rediscache.api;

import com.stonemason.rediscache.dao.entity.Product;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface ProductApi {


    @PostMapping("/create")
    @ApiOperation(value = "createProduct")
    ResponseEntity<Product> createProduct(@RequestParam("name") String name);

    @GetMapping("/get")
    @ApiOperation(value = "getProduct")
    ResponseEntity<Product> getProduct(@RequestParam("id") int id);

    @PutMapping("/update")
    @ApiOperation(value = "updateProduct")
    ResponseEntity<Product> updateProduct(@RequestParam("id") int id, @RequestParam("name") String name);

}
