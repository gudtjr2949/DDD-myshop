package com.ddd.order.command.domain;

import com.ddd.common.model.Address;
import com.ddd.common.model.Money;
import com.ddd.member.command.domain.MemberId;
import com.ddd.order.command.domain.product.ProductId;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ddd.order.command.domain.OrderState.*;
import static com.ddd.order.command.domain.OrderState.PAYMENT_WAITING;
import static org.assertj.core.api.Assertions.*;

class OrderTest {

    @Test
    @DisplayName("Order 를 저장하면, 전체 금액이 계산되어 Order 와 함께 저장된다.")
    void saveOrderAndCheckTotalMoney() {
        // given
        OrderLine orderLine1 = createOrderLine("1", 10_000, 1);
        OrderLine orderLine2 = createOrderLine("2", 30_000, 2);
        Orderer orderer = createOrderer("1", "gudtjr2949@naver.com", "이형석", "010-7127-2949");
        Address address = createAddress("34151", "대전광역시", "유성구", "덕명동", "597-5 404호");
        Receiver receiver = createReceiver("이형석", "010-7127-2949");
        ShippingInfo shippingInfo = createShippingInfo(address, receiver, "특이사항 없음");

        Order order = createOrder("1", orderer, PAYMENT_WAITING, List.of(orderLine1, orderLine2), shippingInfo);

        // when & then
        assertThat(order.getTotalAmounts().getValue())
                .isEqualTo(List.of(orderLine1, orderLine2).stream().mapToInt(x -> x.getAmounts()).sum());
    }

    @Test
    @DisplayName("Order 를 저장하기 위해 입력한 파라미터 중 null 이 있다면 예외를 던진다.")
    void throwsExceptionWhenNullInput() {
        // given
        OrderLine orderLine1 = createOrderLine("1", 10_000, 1);
        OrderLine orderLine2 = createOrderLine("2", 30_000, 2);
        Orderer orderer = createOrderer("1", "gudtjr2949@naver.com", "이형석", "010-7127-2949");
        Address address = createAddress("34151", "대전광역시", "유성구", "덕명동", "597-5 404호");
        Receiver receiver = createReceiver("이형석", "010-7127-2949");
        ShippingInfo shippingInfo = createShippingInfo(address, receiver, "특이사항 없음");

        // when & then
        assertThatThrownBy(() -> createOrder(null, orderer, PAYMENT_WAITING, List.of(orderLine1, orderLine2), shippingInfo))
                .hasMessage("no id").isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> createOrder("1", null, PAYMENT_WAITING, List.of(orderLine1, orderLine2), shippingInfo))
                .hasMessage("no orderer").isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> createOrder("1", orderer, PAYMENT_WAITING, null, shippingInfo))
                .hasMessage("no OrderLine").isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> createOrder("1", orderer, PAYMENT_WAITING, List.of(orderLine1, orderLine2), null))
                .hasMessage("no shipping info").isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("주문 상태가 '출고' 이거나 '배송 준비중' 이라면 배송지 정보를 변경할 수 없다.")
    void canNotChangeShippingInfoBecauseOrderState() {
        // given
        OrderLine orderLine1 = createOrderLine("1", 10_000, 1);
        OrderLine orderLine2 = createOrderLine("2", 30_000, 2);
        Orderer orderer = createOrderer("1", "gudtjr2949@naver.com", "이형석", "010-7127-2949");
        Address address = createAddress("34151", "대전광역시", "유성구", "덕명동", "597-5 404호");
        Address newAddress = createAddress("32346", "경상북도", "구미시", "송정동", "597-5 404호");

        Receiver receiver = createReceiver("이형석", "010-7127-2949");
        ShippingInfo shippingInfo = createShippingInfo(address, receiver, "특이사항 없음");
        ShippingInfo newShippingInfo = createShippingInfo(newAddress, receiver, "집 앞 개 조심");

        Order deliveringOrder = createOrder("1", orderer, DELIVERING, List.of(orderLine1, orderLine2), shippingInfo);
        Order shippedOrder = createOrder("1", orderer, SHIPPED, List.of(orderLine1, orderLine2), shippingInfo);

        // when & then
        assertThatThrownBy(() -> deliveringOrder.changeShippingInfo(newShippingInfo))
                .hasMessage(deliveringOrder.getOrderState().name() + " 주문 상태로 인한 배송지 변경 불가능")
                .isInstanceOf(IllegalStateException.class);

        assertThatThrownBy(() -> shippedOrder.changeShippingInfo(newShippingInfo))
                .hasMessage(shippedOrder.getOrderState().name() + " 주문 상태로 인한 배송지 변경 불가능")
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("주문 상태가 '출고' 이거나 '배송 준비중' 이라면 주문 상품 정보를 변경할 수 없다.")
    void canNotChangeOrderLinesBecauseOrderState() {
        // given
        OrderLine orderLine1 = createOrderLine("1", 10_000, 1);
        OrderLine orderLine2 = createOrderLine("2", 30_000, 2);
        Orderer orderer = createOrderer("1", "gudtjr2949@naver.com", "이형석", "010-7127-2949");
        Address address = createAddress("34151", "대전광역시", "유성구", "덕명동", "597-5 404호");
        Receiver receiver = createReceiver("이형석", "010-7127-2949");
        ShippingInfo shippingInfo = createShippingInfo(address, receiver, "특이사항 없음");

        List<OrderLine> newOrderLinde = List.of(orderLine1);

        Order deliveringOrder = createOrder("1", orderer, DELIVERING, List.of(orderLine1, orderLine2), shippingInfo);
        Order shippedOrder = createOrder("1", orderer, SHIPPED, List.of(orderLine1, orderLine2), shippingInfo);

        // when & then
        assertThatThrownBy(() -> deliveringOrder.changeOrderLines(newOrderLinde))
                .hasMessage(deliveringOrder.getOrderState().name() + " 주문 상태로 인한 상품 변경 불가능")
                .isInstanceOf(IllegalStateException.class);

        assertThatThrownBy(() -> shippedOrder.changeOrderLines(newOrderLinde))
                .hasMessage(shippedOrder.getOrderState().name() + " 주문 상태로 인한 상품 변경 불가능")
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("주문 상태가 '출고' 이거나 '배송 준비중' 이라면 주문을 취소할 수 없다.")
    void canNotCancelOrderBecauseOrderState() {
        // given
        OrderLine orderLine1 = createOrderLine("1", 10_000, 1);
        OrderLine orderLine2 = createOrderLine("2", 30_000, 2);
        Orderer orderer = createOrderer("1", "gudtjr2949@naver.com", "이형석", "010-7127-2949");
        Address address = createAddress("34151", "대전광역시", "유성구", "덕명동", "597-5 404호");
        Receiver receiver = createReceiver("이형석", "010-7127-2949");
        ShippingInfo shippingInfo = createShippingInfo(address, receiver, "특이사항 없음");

        Order deliveringOrder = createOrder("1", orderer, DELIVERING, List.of(orderLine1, orderLine2), shippingInfo);
        Order shippedOrder = createOrder("1", orderer, SHIPPED, List.of(orderLine1, orderLine2), shippingInfo);

        // when & then
        assertThatThrownBy(() -> deliveringOrder.cancelOrder())
                .hasMessage(deliveringOrder.getOrderState().name() + " 주문 상태로 인한 주문 취소 불가능")
                .isInstanceOf(IllegalStateException.class);

        assertThatThrownBy(() -> shippedOrder.cancelOrder())
                .hasMessage(shippedOrder.getOrderState().name() + " 주문 상태로 인한 주문 취소 불가능")
                .isInstanceOf(IllegalStateException.class);
    }

    private static Order createOrder(String orderId, Orderer orderer, OrderState orderState, List<OrderLine> orderLines,
                                     ShippingInfo shippingInfo) {
        return Order.builder()
                .id(new OrderId(orderId))
                .orderer(orderer)
                .orderState(orderState)
                .orderLines(orderLines)
                .shippingInfo(shippingInfo)
                .build();
    }

    private static ShippingInfo createShippingInfo(Address address, Receiver receiver, String message) {
        return ShippingInfo.builder()
                // 대전 유성구 덕명동 597-5 (34151)
                .address(address)
                .receiver(receiver)
                .message(message)
                .build();
    }

    private static Orderer createOrderer(String memberId, String email, String name, String phone) {
        return Orderer.builder().id(new MemberId(memberId)).email(email)
                .name(name).phone(phone).build();
    }

    private static Receiver createReceiver(String name, String phone) {
        return Receiver.builder()
                .name(name)
                .phone(phone)
                .build();
    }

    private static Address createAddress(String postalCode, String state, String city, String district,
                                         String streetAddress) {
        return Address.builder()
                .postalCode(postalCode)
                .state(state)
                .city(city)
                .district(district)
                .streetAddress(streetAddress)
                .build();
    }

    private static OrderLine createOrderLine(String id, int value, int quantity) {
        return OrderLine.builder()
                .productId(new ProductId(id))
                .price(new Money(value))
                .quantity(quantity)
                .build();
    }
}