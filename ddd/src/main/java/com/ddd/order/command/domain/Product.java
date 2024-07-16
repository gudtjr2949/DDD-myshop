package com.ddd.order.command.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Product {
    private Category category;
    private String name;
    private Money price;
}