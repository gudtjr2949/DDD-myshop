package com.ddd.member.command.domain;

public interface MemberRepository {
    Member findById(String memberId);
    Member findByEmail(String email);
}
