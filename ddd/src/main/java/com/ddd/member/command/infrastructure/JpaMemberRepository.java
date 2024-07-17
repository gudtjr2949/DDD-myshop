package com.ddd.member.command.infrastructure;

import com.ddd.member.command.domain.Member;
import com.ddd.member.command.domain.MemberId;
import com.ddd.order.command.infrastructure.JpaOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaMemberRepository extends JpaRepository<Member, MemberId> {
    Optional<Member> findMemberByEmail(String email);
}
