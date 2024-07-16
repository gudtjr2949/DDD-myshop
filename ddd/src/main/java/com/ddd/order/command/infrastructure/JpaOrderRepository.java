package com.ddd.order.command.infrastructure;

import com.ddd.order.command.domain.Order;
import com.ddd.order.command.domain.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends JpaRepository<Order, OrderId> {
}