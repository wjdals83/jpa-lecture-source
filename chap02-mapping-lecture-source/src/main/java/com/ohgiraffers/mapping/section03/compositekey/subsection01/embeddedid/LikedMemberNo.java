package com.ohgiraffers.mapping.section03.compositekey.subsection01.embeddedid;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class LikedMemberNo {

    @Column(name = "liked_member_no")
    private int likedMemberNo;

    protected LikedMemberNo() {}

    public LikedMemberNo(int likedBookNo) {
        this.likedMemberNo = likedBookNo;
    }

}
