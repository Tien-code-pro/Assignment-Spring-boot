package com.example.orderdemo.entity.search;

public enum SearchCriteriaOperator {
    EQUALS, NOT_EQUALS,
    GREATER_THAN, GREATER_THAN_OR_EQUALS,
    LESS_THAN, LESS_THAN_OR_EQUALS,
    LIKE,
    IN,
    JOIN_USERNAME, JOIN_DETAIL_PRODUCT, JOIN_USER,
    JOIN;
}
