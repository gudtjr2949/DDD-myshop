package com.ddd.order.command.domain;

public interface OrderRepository {
    Order findById(String orderId);
}
