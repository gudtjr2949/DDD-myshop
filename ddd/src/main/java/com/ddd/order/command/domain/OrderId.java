package com.ddd.order.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Embeddable
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderId implements Serializable {

    @Column(name = "order_id")
    private String id;
}
