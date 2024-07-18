package com.ddd.order.command.domain.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class Category {

    @EmbeddedId
    private CategoryId id;

    @Column(name = "category_name")
    private String name;

    @ElementCollection
    @CollectionTable(name = "product_category",
            joinColumns = @JoinColumn(name = "category_id")) // 연결 테이블 생성
    private Set<ProductId> productIds;

}
