package com.ddd.order.command.domain.product;

import org.springframework.data.domain.Page;

public interface ProductRepository {
    Product findById(String productId);
    Page<Product> findProductsByCategoryId(String categoryId, int page, int size);
    int countsByCategoryId(String categoryId);
}
