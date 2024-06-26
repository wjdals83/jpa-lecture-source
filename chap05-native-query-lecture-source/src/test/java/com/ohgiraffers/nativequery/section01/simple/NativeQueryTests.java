package com.ohgiraffers.nativequery.section01.simple;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class NativeQueryTests {

    /* 필기.
    *   Native Query 란 Mysql 에서 작성했던 쿼리문을 그대로 사용하는 것을 의미한다.
    *   이를 사용하게 된다면 ORM 의 기능을 이용하면서, SQL 쿼리를 사용할 수 있어서
    *   더욱 강력하게 데이터베이스에 접근할 수 있게 된다.
    *   따라서 복잡한 쿼리를 작성할 때나, 특정한 데이터베이스에서만 사용이 가능한
    *   기능을 사용해야 할 때 등에 Native Query 를 사용한다.
    *  */

    /* 필기.
    *   1. 결과 타입 정의가 가능할 때
    *   2. 결과 타입을 정의할 수 없을 때
    *   3. 결과 매핑 사용
    *  */

    @PersistenceContext
    private EntityManager manager;

    /* 1. 결과 타입을 정의한 경우 */
    /* 필기.
    *   모든 컬럼값을 매핑하는 경우에만 타입을 특정할 수 있다.
    *   일부 컬럼만 조회를 하고 싶다? -> Object[], 스칼라 값을 별도로 담을
    *   클래스를 정의해서 사용해야 한다.
    *  */
    @DisplayName("결과 타입을 정의한 Native Query 사용해보기")
    @Test
    @Transactional      // transactional 을 추가해주지 않으면 오류가 ㅏ발생한다. -> 캐시에 저장하는 느낌,,??
    void testNativeQueryByResultType() {

        //given
        int menuCode = 15;

        //when
        String query = "SELECT menu_code, menu_name, menu_price, category_code, orderable_status" +
                " FROM tbl_menu" +
                " WHERE menu_code = ?";

        Query nativeQuery = manager.createNativeQuery(query, Menu.class).setParameter(1, menuCode);    // 모든 필드를 매핑하므로 타입을 특정할 수 있다.
        Menu foundMenu = (Menu) nativeQuery.getSingleResult();   //query 의 반환값은 object 이거나 object[] 이므로 형변환 필요

        Assertions.assertNotNull(foundMenu);    // 결과 담겼니?
        Assertions.assertTrue(manager.contains(foundMenu));     //결과를 영속성 컨테이너에 보관하고 있는지
        System.out.println("foundMenu = " + foundMenu);

    }

    @DisplayName("결과 타입을 지정할 수 없는 Native Query 테스트")
    @Test
    void testNativeQueryNoResult() {

        String query = "SELECT menu_name, menu_price FROM tbl_menu";
        List<Object[]> menuList = manager.createNativeQuery(query).getResultList();

        Assertions.assertNotNull(menuList);
        menuList.forEach(
                row -> {
                    for(Object col : row) {
                        System.out.print(col + " ");
                    }
                    System.out.println();
                }
        );

    }

    /* 3. 결과 매핑을 사용하는 경우 - 자동, - 수동 */
    @DisplayName("자동 결과 매핑을 사용한 Native Query 조회 테스트")
    @Test
    @Transactional
    void testAutoMapping() {

        /* 상황 : category 를 기준으로 카테고리 별 메뉴의 개수를 조회하고 싶다. */
        // COALESCE 함수 : 여러 개의 인수(컬럼)를 전달할 수 있으며, Left Join 으로 인해 null 이 발생할 수 있기 때문에 0을 넣어주겠다.
        String query = "SELECT a.category_code, a.category_name, a.ref_category_code, COALESCE(v.menu_count, 0) menu_count" +
                " FROM tbl_category a" +
                " LEFT JOIN (SELECT COUNT(*) AS menu_count, b.category_code" +
                            " FROM tbl_menu b" +
                            " GROUP BY b.category_code) v ON (a.category_code = v.category_code)" +
                " ORDER BY 1";
        // order by 1 -> 첫 번쨰 컬럼을 기준으로 내림차순 정렬

        /* 필기.
        *    자동 매핑을 사용하는 이유 ? -> Object 로 값을 받는다. 하지만 우리가 값을 조작하거나 다시 데이터와 맞출 때
        *    Object 타입에서 우리가 원하는 대로 형변환을 해줘야 한다.
        *    이는 entity 필드도 고려, 데이터베이스의 자료형도 고려해야 하는 작업이기 때문에 번거롭다.
        * */
        Query nativeQuery = manager.createNativeQuery(query, "categoryAutoMapping");
        List<Object[]> categoryList = nativeQuery.getResultList();

        Assertions.assertNotNull(categoryList);
        Assertions.assertTrue(manager.contains(categoryList.get(0)[0]));        //0번 째 인덱스의 0번 째

        System.out.println("categoryList.get(0)[0] : " + categoryList.get(0)[0]);
        System.out.println("categoryList.get(0)[1] : " + categoryList.get(0)[1]);

        System.out.println("categoryList.get(0)[0] : " + categoryList.get(0)[0].getClass());
        System.out.println("categoryList.get(0)[1] : " + categoryList.get(0)[1].getClass());

        categoryList.forEach(
                row -> {
                    for(Object col : row) {
                        System.out.print(col + " / ");
                    }
                    System.out.println();
                }
        );

    }

    /* 3. 결과 매핑을 사용하는 경우 -자동, -수동 */
    @DisplayName("수동 결과 매핑을 사용한 Native Query 조회 테스트")
    @Test
    @Transactional
    void testManualMapping() {

        /* 상황 : category 를 기준으로 카테고리 별 메뉴의 개수를 조회하고 싶다. */
        // COALESCE 함수 : 여러 개의 인수(컬럼)를 전달할 수 있으며  Left Join 으로 인해 null 이 발생 할 수 있기 때문에 0을 넣어주겠다.
        String query = "SELECT a.category_code, a.category_name, a.ref_category_code, COALESCE(v.menu_count, 0) menu_count" +
                " FROM tbl_category a" +
                " LEFT JOIN (SELECT COUNT(*) AS menu_count, b.category_code" +
                " FROM tbl_menu b" +
                " GROUP BY b.category_code) v ON (a.category_code = v.category_code)" +
                " ORDER BY 1";
        // order by 1 -> 첫 번째 컬럼을 기준으로 내림차순 정렬

        /* 필기.
            자동 매핑을 사용하는 이유 ? -> Object 로 값을 받는다. 하지만 우리가 값을 조작하거나 다시 데이터와 맞출 때
            Object 타입에서 우리가 원하는 대로 형변환을 해줘야 한다.
            이는 entity 필드도 고려, 데이터베이스의 자료형도 고려해야 하는 작업이기 때문에 번거롭다.
        */
        Query nativeQuery = manager.createNativeQuery(query, "categoryManualMapping");
        List<Object[]> categoryList = nativeQuery.getResultList();

        Assertions.assertNotNull(categoryList);
        Assertions.assertTrue(manager.contains(categoryList.get(0)[0]));

        System.out.println("categoryList.get(0)[0] : " + categoryList.get(0)[0]);
        System.out.println("categoryList.get(0)[1] : " + categoryList.get(0)[1]);

        System.out.println("categoryList.get(0)[0] : " + categoryList.get(0)[0].getClass());
        System.out.println("categoryList.get(0)[0] : " + categoryList.get(0)[1].getClass());

        categoryList.forEach(
                row -> {
                    for(Object col : row) {
                        System.out.print(col + " / ");
                    }
                    System.out.println();
                }
        );

    }

}
