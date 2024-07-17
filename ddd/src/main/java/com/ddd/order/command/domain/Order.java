package com.ddd.order.command.domain;

import com.ddd.common.MoneyConverter;
import com.ddd.common.model.Money;
import jakarta.persistence.*;
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

    public Order(OrderId id, Orderer orderer, List<OrderLine> orderLines,
                 ShippingInfo shippingInfo, OrderState orderState) {
        setId(id);
        setOrderer(orderer);
        setOrderLines(orderLines);
        setShippingInfo(shippingInfo);
        this.orderState = orderState;
    }

    private void setId(OrderId id) {
        if (id == null) throw new IllegalArgumentException("no id");
        this.id = id;
    }

    private void setOrderer(Orderer orderer) {
        if (orderer == null) throw new IllegalArgumentException("no orderer");
        this.orderer = orderer;
    }

    private void setOrderLines(List<OrderLine> orderLines) {
        verifyAtLeastOneOrMoreOrderLines(orderLines);
        this.orderLines = new OrderLines(orderLines);
        this.totalAmounts = this.orderLines.getTotalAmounts();
    }

    private void setShippingInfo(ShippingInfo shippingInfo) {
        if (shippingInfo == null) throw new IllegalArgumentException("no shipping info");
        this.shippingInfo = shippingInfo;
    }

//    private void calculateTotalAmounts() {
//        int sum = orderLines.getLines().stream().mapToInt(o -> o.getAmounts()).sum();
//        this.totalAmounts = new Money(sum);
//    }

    private void verifyAtLeastOneOrMoreOrderLines(List<OrderLine> orderLines) {
        if (orderLines == null || orderLines.isEmpty()) {
            throw new IllegalArgumentException("no OrderLine");
        }
    }

    public void changeOrderLines(List<OrderLine> newLines) {
        orderLines.changeOrderLines(newLines);
        this.totalAmounts = orderLines.getTotalAmounts();
    }

    /**
     * 배송지 변경 메서드
     * @param newShippingInfo : 변경할 배송지 정보
     * orderState 가 출고되었거나 배송 준비중이라면 배송지 정보 변경 불가
     */
    public void changeShippingInfo(ShippingInfo newShippingInfo) {
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
