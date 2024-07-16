package com.ddd.order.command.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("OrderRepository 빈이 등록되었다.")
    void OrderRepositoryBeanShouldBeRegistered() {
        assertThat(orderRepository).isNotNull();
    }

}