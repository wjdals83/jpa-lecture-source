package com.ohgiraffers.section02.crud;

import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

public class EntityManagerCRUDTests {

    private EntityManagerCRUD crud;

    @BeforeEach     // test 코드가 실행되기 전마다 instance 를 생성해 준다.
    void initManager() {
        this.crud = new EntityManagerCRUD();
    }

    @AfterEach    // 검즈잉끝나면 원상태로
    void rollback() {

        EntityTransaction transaction = crud.getManagerInstance().getTransaction();
        transaction.rollback();

    }

    @DisplayName("메뉴 코드로 메뉴 조회 테스트")
    /* 필기.
    *   테스트 시에 여러 값들을 이용해서 검증을 진행해야 하는 경우에 경우의 수 만큼 테스트 메소드를
    *   작성해야 한다.
    *   @ParameterizedTest 어노테이션을 붙이게 되면 테스트 메소드에 매개변수를 선언할 수 있다.
    *   (일반적인 테스트 메소드는 매개변수 사용 불가)
    *   파라키터로 전달할 값의 목록 만큼 반복적으로 테스트 메소드를 실행하며 준비 된 값 목록을 전달하여
    *   테스트를 실행할 수 있다. --> given 을 대체할 수 있다.
    *  */

    @ParameterizedTest
    /* 필기. 여러 개의 테스트 전용 파라미터 전달. 쉼표(,) 로 값을 구분할 수 있다.  */
    @CsvSource({"5, 5", "8, 8", "7, 7"})
    void testFindMethodByMenuCode(int menuCode, int expected) {

        // when
        Menu foundMenu = crud.findMenuByMenuCode(menuCode);

        // then
        Assertions.assertEquals(expected, foundMenu.getMenuCode());

    }

    private static Stream<Arguments> newMenu() {
        return Stream.of(
                Arguments.of(
                        "신메뉴",
                        20000,
                        4,
                        "Y"
                )
        );
    }

    @DisplayName("새로운 메뉴 추가 테스트")
    @ParameterizedTest
    @MethodSource("newMenu")
    void testRegist(String menuName, int menuPrice, int categoryCode, String orderableStatus) {

        // when
        Menu newMenu = new Menu(menuName, menuPrice, categoryCode, orderableStatus);
        Long count = crud.saveAndReturnAllCount(newMenu);

        // then
        Assertions.assertEquals(22, count);

    }

    @DisplayName("메뉴 이름 수정 테스트")
    @ParameterizedTest
    @CsvSource("1, 변경된 메뉴")
    void testModifyMenuName(int menuCode, String menuName) {

        // when
        Menu modifyMenu = crud.modifyMenuName(menuCode, menuName);

        // then
        Assertions.assertEquals(menuName, modifyMenu.getMenuName());

    }

    @DisplayName("메뉴 삭제 테스트")
    @ParameterizedTest
    @ValueSource(ints = {1})
    void testRemoveMenu(int menuCode) {

        Long count = crud.removeAndReturnAllCount(menuCode);

        Assertions.assertEquals(20, count);

    }

}
