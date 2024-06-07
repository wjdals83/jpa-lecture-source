package com.ohgiraffers.section02.crud;

import jakarta.persistence.*;

@Entity(name = "section02Menu")
@Table(name = "tbl_menu")
public class Menu {


    // pk -> not null, unique -> auto increment
    @Id // not null, unique
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /* strategy 속성
    *   - AUTO : 우리가 사용하는 DB 에 따른다.
    *   - IDENTITY / SEQUENCE : mysql auto increment 사용 oracle sequence
    *  */
    @Column(name = "menu_code")
    private int menuCode;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "orderable_status")
    private String orderableStatus;

    // 같은 패키지 내에 있는 녀석들만 사용 가능하다.
    protected Menu() {}

    public Menu(String menuName, int menuPrice, int categoryCode, String orderableStatus) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.categoryCode = categoryCode;
        this.orderableStatus = orderableStatus;
    }

    public int getMenuCode() {

        return this.menuCode;
    }

    public void setMenuName(String menuName) {

        this.menuName = menuName;
    }

    public String getMenuName() {

        return menuName;
    }
}
