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
                .orElseThrow(() -> new IllegalArgumentException("없는 주문입니다. 주문 ID : " + orderId));

        return byId;
    }

    @Override
    public Order save(Order order) {
        return jpaOrderRepository.save(order);
    }

    @Override
    public void delete(Order order) {
        jpaOrderRepository.delete(order);
    }
}
