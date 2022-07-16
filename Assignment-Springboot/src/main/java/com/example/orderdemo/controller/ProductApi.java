package com.example.orderdemo.controller;

import com.example.orderdemo.entity.Product;
import com.example.orderdemo.entity.dto.ProductDTO;
import com.example.orderdemo.entity.enums.ProductSimpleStatus;
import com.example.orderdemo.repository.ProductRepository;
import com.example.orderdemo.service.ProductService;
import com.example.orderdemo.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping(path = "/api/v1/admin")
public class ProductApi {

    @Autowired
    ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(method = RequestMethod.GET, path = "/products")
    public ResponseEntity<?> getList(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            @RequestParam(name = "name", defaultValue = "", required = false) String name) {
        return ResponseEntity.ok(productService.findAll(
                        PageRequest.of(page - 1, limit), name
                )
        );
    }
}
