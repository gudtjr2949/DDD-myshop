package com.ddd.order.command.infrastructure;

import com.ddd.order.command.domain.product.Category;
import com.ddd.order.command.domain.product.CategoryId;
import com.ddd.order.command.domain.product.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final JpaCategoryRepository jpaCategoryRepository;

    @Override
    public Category findById(String categoryId) {
        Category byId = jpaCategoryRepository.findById(new CategoryId(categoryId))
                .orElseThrow(() -> new IllegalArgumentException("없는 카테고리입니다. 입력 카테고리 아이디 : " + categoryId));

        return byId;
    }
}
