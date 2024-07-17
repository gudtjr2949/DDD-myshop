package com.ddd.order.command.application;

import com.ddd.order.command.domain.product.Category;
import com.ddd.order.command.domain.product.CategoryRepository;
import com.ddd.order.command.domain.product.Product;
import com.ddd.order.command.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductListService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Page<Product> getProductOfCategory(String categoryId, int page, int size) {
        Category category = categoryRepository.findById(categoryId);

        Page<Product> productsByCategoryId = productRepository.findProductsByCategoryId(category.getId().getId(), page, size);

        return productsByCategoryId;
    }
}
