package com.ddd.order.command.domain.product;

import com.ddd.common.MoneyConverter;
import com.ddd.common.model.Money;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name = "product")
public class Product {

    @EmbeddedId
    private ProductId id;

    @ElementCollection
    @CollectionTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id")) // 연결 테이블 생성
    private Set<CategoryId> categoryIds;

    private String name;

    @Convert(converter = MoneyConverter.class)
    private Money price;

    @Builder
    private Product(ProductId id, Set<CategoryId> categoryIds, String name, Money price) {
        setId(id);
        setCategoryIds(categoryIds);
        setName(name);
        setPrice(price);
    }


    private void setCategoryIds(Set<CategoryId> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) throw new IllegalArgumentException("no category id");
        this.categoryIds = categoryIds;
    }

    private void setId(ProductId id) {
        if (id.getId() == null || id == null) throw new IllegalArgumentException("no product id");
        this.id = id;
    }

    private void setName(String name) {
        if (name == null) throw new IllegalArgumentException("no name");
        this.name = name;
    }

    private void setPrice(Money price) {
        if (price.getValue() == 0 || price == null) throw new IllegalArgumentException("no price");
        this.price = price;
    }
}