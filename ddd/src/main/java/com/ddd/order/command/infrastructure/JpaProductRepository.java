package com.ddd.order.command.infrastructure;

import com.ddd.order.command.domain.product.CategoryId;
import com.ddd.order.command.domain.product.Product;
import com.ddd.order.command.domain.product.ProductId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaProductRepository extends JpaRepository<Product, ProductId> {
    Optional<Page<Product>> findProductsByCategoryIds(CategoryId categoryIds, Pageable pageable);
    Optional<Integer> countProductsByCategoryIds(CategoryId categoryIds);
}
