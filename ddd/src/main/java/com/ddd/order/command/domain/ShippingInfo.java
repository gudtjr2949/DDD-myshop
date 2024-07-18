package com.ddd.order.command.domain;

import com.ddd.common.model.Address;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ShippingInfo {
    @Embedded
    private Address address;

    @Embedded
    private Receiver receiver;
    private String message; // 주문 특이사항
}
