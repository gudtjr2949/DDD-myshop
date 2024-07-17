package com.ddd.order.command.infrastructure;

import com.ddd.order.command.domain.product.Category;
import com.ddd.order.command.domain.product.CategoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCategoryRepository extends JpaRepository<Category, CategoryId> {
}
