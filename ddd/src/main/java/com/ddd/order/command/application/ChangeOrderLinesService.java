package com.ddd.order.command.application;

import com.ddd.order.command.domain.Order;
import com.ddd.order.command.domain.OrderLine;
import com.ddd.order.command.domain.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChangeOrderLinesService {

    private final OrderRepository orderRepository;

    @Transactional
    public void changeOrderLines(String orderId, List<OrderLine> newOrderLines) {
        Order order = orderRepository.findById(orderId);
        order.changeOrderLines(newOrderLines);
    }
}
