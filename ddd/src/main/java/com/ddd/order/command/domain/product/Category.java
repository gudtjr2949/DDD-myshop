package com.ddd.order.command.domain.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
