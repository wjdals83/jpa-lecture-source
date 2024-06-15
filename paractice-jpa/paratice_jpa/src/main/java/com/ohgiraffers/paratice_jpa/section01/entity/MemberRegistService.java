package com.ohgiraffers.paratice_jpa.section01.entity;

import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberRegistService {

    private MemberRepository memberRepository;

    @Autowired
    public MemberRegistService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void registMember(MemberRegistDTO newMember) {

        Member member = new Member(
                newMember.getMemberId(),
                newMember.getMemberPwd(),
                newMember.getMemberName(),
                newMember.getPhone(),
                newMember.getAddress(),
                newMember.getEnrollDate(),
                newMember.getMemberRole(),
                newMember.getStatus()
        );

        memberRepository.save(member);

    }

    @Transactional
    public String registMemberAndFindName(MemberRegistDTO newMember) {

        registMember(newMember);

        return memberRepository.findNameById(newMember.getMemberId());
    }
}
