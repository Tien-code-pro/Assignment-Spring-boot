package com.example.orderdemo.entity.search;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchBody {
        private int page;
        private int limit;
        private String nameUser;
        private String phone;
        private String nameProduct;
        private String orderId;
        private String sort;
        private String start;
        private String end;


        public static final class SearchBodyBuilder {
            private int page;
            private int limit;
            private String nameUser;
            private String phone;
            private String nameProduct;
            private String orderId;
            private String sort;
            private String start;
            private String end;

            private SearchBodyBuilder() {
            }

            public static SearchBodyBuilder aSearchBody() {
                return new SearchBodyBuilder();
            }

            public SearchBodyBuilder withPage(int page) {
                this.page = page;
                return this;
            }

            public SearchBodyBuilder withLimit(int limit) {
                this.limit = limit;
                return this;
            }

            public SearchBodyBuilder withNameUser(String nameUser) {
                this.nameUser = nameUser;
                return this;
            }

            public SearchBodyBuilder withPhone(String phone) {
                this.phone = phone;
                return this;
            }

            public SearchBodyBuilder withNameProduct(String nameProduct) {
                this.nameProduct = nameProduct;
                return this;
            }

            public SearchBodyBuilder withOrderId(String orderId) {
                this.orderId = orderId;
                return this;
            }

            public SearchBodyBuilder withSort(String sort) {
                this.sort = sort;
                return this;
            }

            public SearchBodyBuilder withStart(String start) {
                this.start = start;
                return this;
            }

            public SearchBodyBuilder withEnd(String end) {
                this.end = end;
                return this;
            }

            public SearchBody build() {
                SearchBody searchBody = new SearchBody();
                searchBody.setPage(page);
                searchBody.setLimit(limit);
                searchBody.setNameUser(nameUser);
                searchBody.setPhone(phone);
                searchBody.setNameProduct(nameProduct);
                searchBody.setOrderId(orderId);
                searchBody.setSort(sort);
                searchBody.setStart(start);
                searchBody.setEnd(end);
                return searchBody;
            }
        }
    }
