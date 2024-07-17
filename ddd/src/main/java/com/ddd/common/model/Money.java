package com.ddd.common.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Money {
    private int value;

    public Money add(Money money) {
        return new Money(this.value + money.value);
    }

    public Money multiply(int quantity) {
        return new Money(value * quantity);
    }

    public Money disCount() {
        return null;
    }
}
