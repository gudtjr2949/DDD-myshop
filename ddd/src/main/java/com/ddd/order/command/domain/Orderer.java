package com.ddd.order.command.domain;

import com.ddd.member.command.domain.MemberId;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Orderer {

    @Column(name = "orderer_id")
    private MemberId id;

    @Column(name = "orderer_name")
    private String name;

    @Column(name = "orderer_email")
    private String email;

    @Column(name = "orderer_phone")
    private String phone;
}
