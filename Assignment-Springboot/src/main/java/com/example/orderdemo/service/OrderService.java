package com.example.orderdemo.service;

import com.example.orderdemo.entity.Order;
import com.example.orderdemo.entity.search.*;
import com.example.orderdemo.repository.OrderRepository;
import com.example.orderdemo.util.ConvertDateHelper;
import com.example.orderdemo.util.LocalDatetimehelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.orderdemo.entity.search.SearchCriteriaOperator.*;

@Service
public class OrderService {
    final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {

        this.orderRepository = orderRepository;
    }

    public Map<String, Object> findAll(SearchBody searchBody){
        Specification specification = Specification.where(null);

        if (searchBody.getNameUser() != null && searchBody.getNameUser().length() > 0 ){
            specification = specification.and(new OrderSpecification(new SearchCriteria("fullName", JOIN_USER, searchBody.getNameUser())));
        }
        if (searchBody.getPhone() != null && searchBody.getPhone().length() > 0){
            specification = specification.and(new OrderSpecification(new SearchCriteria("phone",JOIN_USER, searchBody.getPhone())));
        }
        if (searchBody.getNameProduct() != null && searchBody.getNameProduct().length() > 0){
            specification = specification.and(new OrderSpecification(new SearchCriteria("name",JOIN_DETAIL_PRODUCT, searchBody.getNameProduct())));
        }
        if (searchBody.getOrderId() != null && searchBody.getOrderId().length() > 0){
            specification = specification.and(new OrderSpecification(new SearchCriteria("id", LIKE,searchBody.getOrderId())));
        }
        if (searchBody.getStart() != null && searchBody.getStart().length() > 0){

            LocalDateTime date = ConvertDateHelper.convertStringToLocalDateTime(searchBody.getStart());

            specification = specification.and(new OrderSpecification(new SearchCriteria("createdAt", GREATER_THAN_OR_EQUALS,date)));
        }
        if (searchBody.getEnd() != null && searchBody.getEnd().length() > 0){
            LocalDateTime date = ConvertDateHelper.convertStringToLocalDateTime(searchBody.getEnd());
            specification = specification.and(new OrderSpecification(new SearchCriteria("createdAt", LESS_THAN_OR_EQUALS,date)));
        }

        Sort sort= Sort.by(Sort.Order.asc("id"));
        if (searchBody.getSort() !=null && searchBody.getSort().length() >0){
            if (searchBody.getSort().contains("desc")){
                sort = Sort.by(Sort.Order.desc("id"));
            }
        }
        Pageable pageable = PageRequest.of(searchBody.getPage() -1, searchBody.getLimit(),sort );
        Page<Order> pageOrder = orderRepository.findAll(specification,pageable);
        List<Order> orderList = pageOrder.getContent();
        Map<String, Object> responses = new HashMap<>();
        responses.put("content",orderList);
        responses.put("currentPage",pageOrder.getNumber() + 1);
        responses.put("totalItems",pageOrder.getTotalElements());
        responses.put("totalPage",pageOrder.getTotalPages());
        return responses;
    }
}
