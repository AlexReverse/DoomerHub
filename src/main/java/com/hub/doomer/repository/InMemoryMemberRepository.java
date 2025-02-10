package com.hub.doomer.repository;

import com.hub.doomer.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Repository
public class InMemoryMemberRepository implements MemberRepository {

    private final List<Member> membersList = Collections.synchronizedList(new LinkedList<>());

    public InMemoryMemberRepository() {
        this.membersList.add(new Member(1, "Name", "SurName"));
    }

    @Override
    public List<Member> findAll() {
        return Collections.unmodifiableList(this.membersList);
    }
}
