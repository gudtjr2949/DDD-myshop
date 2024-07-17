package com.ddd.order.command.domain.product;

public interface CategoryRepository {
    Category findById(String categoryId);
}
