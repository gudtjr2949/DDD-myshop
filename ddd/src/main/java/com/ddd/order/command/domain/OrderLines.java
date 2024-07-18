package com.ddd.order.command.domain;

import com.ddd.common.model.Money;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import lombok.*;

import java.util.List;

@Getter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderLines {

    @ElementCollection(fetch = FetchType.LAZY)
    private List<OrderLine> lines;

    public Money getTotalAmounts() {
        int sum = lines.stream()
                .mapToInt(x -> x.getAmounts()).sum();

        return new Money(sum);
    }

    public void changeOrderLines(List<OrderLine> newLines) {
        this.lines = newLines;
    }
}
