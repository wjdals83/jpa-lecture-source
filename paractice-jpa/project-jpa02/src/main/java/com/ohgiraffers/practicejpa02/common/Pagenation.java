package com.ohgiraffers.practicejpa02.common;

import org.springframework.data.domain.Page;

public class Pagenation {

    public static PagingButton getPaginButtonInfo(Page page) {

        /* 현재 페이지 */
        int currentPage = page.getNumber() + 1;

        /* 버튼 기본 갯수 */
        int defaultButtonCount = 10;

        /* 현재 기준 시작 페이지 계산 */
        int startPage = (int) ((Math.ceil((double) currentPage / defaultButtonCount ) - 1) * defaultButtonCount + 1);

        /* 끝 페이지 계산 */
        int endPage = startPage + defaultButtonCount - 1;

        /* 실제 총 페이지가 endPage 보다 작으면 endPage 를 총 페이지로 */
        if(page.getTotalPages() < endPage) {
            endPage = page.getTotalPages();
        }

        return new PagingButton(currentPage, startPage, endPage);

    }

}
