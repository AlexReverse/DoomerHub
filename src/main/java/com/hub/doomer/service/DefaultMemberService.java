package com.hub.doomer.service;

import com.hub.doomer.entity.Member;
import com.hub.doomer.repository.MemberRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultMemberService implements MemberService {

    private final MemberRepository memberRepository;

    public List<Member> findAllMembers() {
        return this.memberRepository.findAll();
    }
}
