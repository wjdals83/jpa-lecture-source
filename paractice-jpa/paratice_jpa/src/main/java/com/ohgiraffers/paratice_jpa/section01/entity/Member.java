package com.ohgiraffers.paratice_jpa.section01.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "entityMember")
@Table(name = "tbl_member", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"member_id", "phone"})
})
public class Member {

    @Id
    @Column(name = "member_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //자동으로 번호 생성
    private int memberNo;

    @Column(name = "member_id", nullable = false, unique = true, columnDefinition = "varchar(10)")
    private String memberId;

    @Column(name = "member_pwd", nullable = false)
    private String memberPwd;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "address", length = 900)
    private String address;

    @Column(name = "enrollDate")
    private LocalDateTime enrollDate;

    /* 중요.
     *   @Enumerated
     *   - enum 타입 매핑
     *   - ORIGINAL : Enum 타입을 순서로 매핑. 데이터 크기 절약
     *   - STRING : Enum 타입을 문자열로 매핑
     *  */

    @Column(name = "member_role")
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @Column(name = "status", columnDefinition = "char(1) default 'Y'")
    private String status;

    protected Member() {
    }

    public Member(String memberId, String memberPwd, String memberName, String phone, String address, LocalDateTime enrollDate, MemberRole memberRole, String status) {
        this.memberId = memberId;
        this.memberPwd = memberPwd;
        this.memberName = memberName;
        this.phone = phone;
        this.address = address;
        this.enrollDate = enrollDate;
        this.memberRole = memberRole;
        this.status = status;
    }
}