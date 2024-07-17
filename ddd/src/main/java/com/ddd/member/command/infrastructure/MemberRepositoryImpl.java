package com.ddd.member.command.infrastructure;

import com.ddd.member.command.domain.Member;
import com.ddd.member.command.domain.MemberId;
import com.ddd.member.command.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final JpaMemberRepository jpaMemberRepository;

    @Override
    public Member findById(String memberId) {
        Member byId = jpaMemberRepository.findById(new MemberId(memberId))
                .orElseThrow(() -> new IllegalArgumentException("없는 사용자입니다. 입력 아이디 : " + memberId));

        return byId;
    }

    @Override
    public Member findByEmail(String email) {
        Member memberByEmail = jpaMemberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("없는 이메일입니다. 입력 이메일 : " + email));

        return memberByEmail;
    }
}
