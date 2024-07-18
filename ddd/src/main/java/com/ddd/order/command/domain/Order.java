package com.ddd.order.command.domain;

import com.ddd.common.MoneyConverter;
import com.ddd.common.model.Money;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.ddd.order.command.domain.OrderState.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "order")
public class Order {

    @EmbeddedId
    private OrderId id;

    private OrderState orderState;

    @Embedded
    private Orderer orderer;

    @Embedded
    private OrderLines orderLines;

    @Embedded
    private ShippingInfo shippingInfo;

    @Convert(converter = MoneyConverter.class)
    private Money totalAmounts;

    @Builder
    private Order(OrderId id, Orderer orderer, List<OrderLine> orderLines,
                 ShippingInfo shippingInfo, OrderState orderState) {
        setId(id);
        setOrderer(orderer);
        setOrderLines(orderLines);
        setShippingInfo(shippingInfo);
        this.orderState = orderState != null ? orderState : PAYMENT_WAITING;
    }

    private void setId(OrderId id) {
        if (id.getId() == null || id == null) throw new IllegalArgumentException("no id");
        this.id = id;
    }

    private void setOrderer(Orderer orderer) {
        if (orderer == null) throw new IllegalArgumentException("no orderer");
        this.orderer = orderer;
    }

    private void setOrderLines(List<OrderLine> orderLines) {
        verifyAtLeastOneOrMoreOrderLines(orderLines);
        this.orderLines = OrderLines.builder().lines(orderLines).build();
        this.totalAmounts = this.orderLines.getTotalAmounts();
    }

    private void setShippingInfo(ShippingInfo shippingInfo) {
        if (shippingInfo == null) throw new IllegalArgumentException("no shipping info");
        this.shippingInfo = shippingInfo;
    }

    private void verifyAtLeastOneOrMoreOrderLines(List<OrderLine> orderLines) {
        if (orderLines == null || orderLines.isEmpty()) {
            throw new IllegalArgumentException("no OrderLine");
        }
    }

    public void changeOrderLines(List<OrderLine> newLines) {
        if (!orderState.isOrderLinesChangeable()) {
            throw new IllegalStateException(orderState + " 주문 상태로 인한 상품 변경 불가능");
        }

        orderLines.changeOrderLines(newLines);
        this.totalAmounts = orderLines.getTotalAmounts();
    }

    public void changeShippingInfo(ShippingInfo newShippingInfo) {
        if (newShippingInfo == null) {
            throw new IllegalArgumentException("no new shipping info");
        }

        if (!orderState.isShippingChangeable()) {
            throw new IllegalStateException(orderState + " 주문 상태로 인한 배송지 변경 불가능");
        }

        this.shippingInfo = newShippingInfo;
    }

    public void cancelOrder() {
        if (!orderState.isOrderCancelable()) {
            throw new IllegalStateException(orderState + " 주문 상태로 인한 주문 취소 불가능");
        }
        this.orderState = CANCEL;
    }

    public void completePayment() {
        this.orderState = PREPARING;
    }
}
