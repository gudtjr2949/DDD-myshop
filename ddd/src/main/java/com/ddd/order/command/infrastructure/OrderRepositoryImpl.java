package com.ddd.order.command.infrastructure;

import com.ddd.order.command.domain.Order;
import com.ddd.order.command.domain.OrderId;
import com.ddd.order.command.domain.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final JpaOrderRepository jpaOrderRepository;

    @Override
    public Order findById(String orderId) {
        Order byId = jpaOrderRepository.findById(new OrderId(orderId))
                .orElseThrow(() -> new IllegalArgumentException("없는 주문 ID 입니다."));

        return byId;
    }
}
