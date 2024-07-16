package com.ddd.order.command.application;

import com.ddd.order.command.domain.Order;
import com.ddd.order.command.domain.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CancelOrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public void cancelOrder(String orderId) {
        Order order = orderRepository.findById(orderId);
        order.cancelOrder();
    }
}
