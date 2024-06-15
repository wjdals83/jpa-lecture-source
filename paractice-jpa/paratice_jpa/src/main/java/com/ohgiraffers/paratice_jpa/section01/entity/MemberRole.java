package com.ohgiraffers.paratice_jpa.section01.entity;

// enum : Enumerated type
public enum MemberRole {
    // 필드가 모두 상수이므로 대문자로 작성
    /* 중요.
     *   enum 타입을 사용하면 코드의 가독성이 향상된다.
     *   type safety(타입의 안정성) 을 보장할 수 있다.
     *   연관된 상수들의 집합 클래스이다.
     *  */

    ROLE_MEMBER,
    ROLE_ADMIN

}