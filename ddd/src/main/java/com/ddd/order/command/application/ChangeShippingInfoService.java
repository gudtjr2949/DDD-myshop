package com.ddd.order.command.application;

import com.ddd.order.command.domain.Order;

import com.ddd.order.command.domain.ShippingInfo;
import com.ddd.order.command.domain.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangeShippingInfoService {

    private final OrderRepository orderRepository;

    @Transactional
    public void changeShippingInfo(String orderId, ShippingInfo newShippingInfo) {
        Order order = orderRepository.findById(orderId);
        order.changeShippingInfo(newShippingInfo);
    }
}
