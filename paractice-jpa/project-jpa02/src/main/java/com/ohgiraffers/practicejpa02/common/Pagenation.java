package com.ohgiraffers.practicejpa02.common;

import org.springframework.data.domain.Page;

public class Pagenation {

    public static PagingButton getPaginButtonInfo(Page page) {

        int currentPage = page.getNumber() + 1;

        int defaultButtonCount = 10;

        int starPage = Math.ceil(double) currentPage /

    }

}
