package com.ddd.order.command.infrastructure;

import com.ddd.order.command.domain.product.CategoryId;
import com.ddd.order.command.domain.product.Product;
import com.ddd.order.command.domain.product.ProductId;
import com.ddd.order.command.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final JpaProductRepository jpaProductRepository;

    @Override
    public Product findById(String productId) {
        Product byId = jpaProductRepository.findById(new ProductId(productId))
                .orElseThrow(() -> new IllegalArgumentException("없는 제품입니다. 입력 제품 아이디 : " + productId));

        return byId;
    }

    @Override
    public Page<Product> findProductsByCategoryId(String categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Product> products = jpaProductRepository.findProductsByCategoryIds(new CategoryId(categoryId), pageable)
                .orElseThrow(() -> new IllegalArgumentException("없는 카테고리입니다. 입력 카테고리 아이디 : " + categoryId));

        return products;
    }

    @Override
    public int countsByCategoryId(String categoryId) {
        Integer count = jpaProductRepository.countProductsByCategoryIds(new CategoryId(categoryId))
                .orElseThrow(() -> new IllegalArgumentException("없는 카테고리입니다. 입력 카테고리 아이디 : " + categoryId));

        return count;
    }
}
