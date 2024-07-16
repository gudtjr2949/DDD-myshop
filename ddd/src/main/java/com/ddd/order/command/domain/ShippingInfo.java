package com.ddd.order.command.domain;

import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShippingInfo {
    @Embedded
    private Address address;

    @Embedded
    private Receiver receiver;
    private String message; // 주문 특이사항
}
