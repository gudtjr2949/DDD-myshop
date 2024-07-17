package com.ddd.member.command.domain;

import com.ddd.common.model.Address;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class Member {

    @EmbeddedId
    private MemberId id;

    @Column(name = "member_name")
    private String name;

    private String email;

    private String password;

    @Embedded
    @Column(name = "member_address")
    private Address address;

    public void changeMemberAddress(Address newAddress) {
        this.address = newAddress;
    }
}
