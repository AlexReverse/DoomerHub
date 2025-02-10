package com.hub.doomer.repository;

import com.hub.doomer.entity.Member;

import java.util.List;

public interface MemberRepository {
    List<Member> findAll();
}
