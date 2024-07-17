package com.ddd.order.command.application;

import com.ddd.member.command.domain.Member;
import com.ddd.member.command.domain.MemberRepository;
import com.ddd.order.command.domain.Order;

import com.ddd.order.command.domain.ShippingInfo;
import com.ddd.order.command.domain.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangeShippingInfoService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void changeShippingInfo(String orderId, ShippingInfo newShippingInfo, boolean useNewShippingAddrAsMemberAddr) {
        Order order = orderRepository.findById(orderId);
        order.changeShippingInfo(newShippingInfo);

        if (useNewShippingAddrAsMemberAddr) {
            Member byId = memberRepository.findById(order.getOrderer().getId().getId());
            byId.changeMemberAddress(newShippingInfo.getAddress());
        }
    }
}
