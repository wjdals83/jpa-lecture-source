package com.ohgiraffers.mapping.section03.compositekey.subsection01.embeddedid;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LikeBookService {

    @Autowired
    private LikeRepository likeRepository;

    @Transactional
    public void generateLikeBook(LikeDTO likeDTO) {

        Like like = new Like(
                new LikedCompositeKey(
                        new LikedMemberNo(likeDTO.getLikedMemberNo()),
                        new LikedBookNo(likeDTO.getLikedBookNo())
                )
        );

        likeRepository.save(like);

    }
}
