package com.ddd.order.command.domain;

public interface OrderRepository {
    Order findById(String orderId);
    Order save(Order order);
    void delete(Order order);
}
