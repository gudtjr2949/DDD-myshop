package com.ddd.order.command.domain;

import com.ddd.common.MoneyConverter;
import com.ddd.common.model.Money;
import com.ddd.order.command.domain.product.Product;
import com.ddd.order.command.domain.product.ProductId;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class OrderLine {

    @Embedded
    private ProductId productId;

    @Convert(converter = MoneyConverter.class)
    private Money price;

    private int quantity;

    @Convert(converter = MoneyConverter.class)
    private Money amounts;

    public OrderLine(ProductId productId, Money price, int quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        this.amounts = calculateAmounts(price, quantity);
    }

    public Money calculateAmounts(Money price, int quantity) {
        return price.multiply(quantity);
    }

    public int getAmounts() {
        return amounts.getValue();
    }
}