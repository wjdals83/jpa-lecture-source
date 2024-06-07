package com.ohgiraffers.mapping.section03.compositekey.subsection01.embeddedid;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/* 예약어 항상 조심 */
@Entity
@Table(name = "tbl_like")
public class Like {

    /* 우리가 세트로 묶어 둔 MemberNo, bookNo 를 ID(pk) 로써 사용 */
    @EmbeddedId
    private LikedCompositeKey compositeKey;

    protected Like() {}

    public Like(LikedCompositeKey compositeKey) {
        this.compositeKey = compositeKey;
    }
}
