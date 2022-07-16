package com.example.orderdemo.service;

import com.example.orderdemo.entity.Order;
import com.example.orderdemo.entity.Product;
import com.example.orderdemo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Map<String, Object> findAll(Pageable pageable, String name) {
        Map<String, Object> responses = new HashMap<>();
        if (name != null || name != ""){
            Page<Product> pageTu = productRepository.findByNameContaining(pageable,name);
            List<Product> list = pageTu.getContent();
            responses.put("content",list); // chi tiết các phần tử được
            responses.put("currentPage",pageTu.getNumber() + 1);// trang hiện tại
            responses.put("totalItems",pageTu.getTotalElements()    );// tổng số phàn tử
            responses.put("totalPage",pageTu.getTotalPages()); // tổng só trang
        }else {
        Page<Product> pageTu = productRepository.findAll(pageable);
        List<Product> list = pageTu.getContent();
        responses.put("content",list); // chi tiết các phần tử được
        responses.put("currentPage",pageTu.getNumber() + 1);// trang hiện tại
        responses.put("totalItems",pageTu.getTotalElements());// tổng số phàn tử
        responses.put("totalPage",pageTu.getTotalPages()); // tổng só trang
    }
        return responses;
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(String.valueOf(id));
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(String.valueOf(id));
    }
}
