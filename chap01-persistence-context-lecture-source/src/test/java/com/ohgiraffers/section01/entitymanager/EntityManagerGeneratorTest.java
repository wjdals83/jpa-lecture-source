package com.ohgiraffers.section01.entitymanager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

public class EntityManagerGeneratorTest {

    /* All -> before : 최초 한 번 테스트 코드가 실행하기 전 동작 */
    @BeforeAll
    static void beforeAllTest() {
        System.out.println("=====================BeforeAll====================");
    }

    @BeforeEach
    void beforeEachTest() {
        System.out.println("==================BeforeEach===============");
    }
    @AfterEach
    void afterEachTest() {
        System.out.println("=================AfterEach=================");
    }
    @AfterAll
    static void afterAllTest() {
        System.out.println("==================AfterAll=================");
    }

    /* 수업목표. Persistence Context 를 이해하기 위한 엔티티 매니저와 팩토리 */

    /* 필기.
    *   엔티티 매니저 팩토리
    *   엔티티 매니저를 생성할 수 있는 기능을 제공하는 팩토리 클래스이다.
    *   엔티티 매니저 팩토리는 thread-safe 하기 때문에 여러 쓰레드가
    *   동시에 접근해도 안전하기 때문에 공유해서 재사용을 한다.
    *   thread-safe 한 기능들은 매번 생성하기 에는 비용(시간, 메모리)
    *   부담이 크기 떄문에 application 스코프와 동일하게
    *   singleton 으로 생성해서 관리하는 것이 효율적이다.
    *   따라서 데이터베이스를 사용하는 어플리케이션 당 한 개의
    *   EntityManagerFactory 를 생성한다.
    *  */

    @Test
    @DisplayName("엔티티 매니저 팩토리 생성 확인하기")
    void testCreateFactory() {

        EntityManagerFactory factory = EntityManagerFactoryGenerator.getInstance();

        System.out.println("엔티티 매니저 팩토리 hashcode : " + factory.hashCode());

        Assertions.assertNotNull(factory);

    }

    @Test
    @DisplayName("엔티티 매니저 팩토리 싱글톤 인스턴스 확인")
    void testFactoryIsSingle() {

        //given
        EntityManagerFactory factory1 = EntityManagerFactoryGenerator.getInstance();
        //when
        EntityManagerFactory factory2 = EntityManagerFactoryGenerator.getInstance();
        //then
        System.out.println("factory1 해쉬코드 : " + factory1.hashCode());
        System.out.println("factory2 해쉬코드 : " + factory2.hashCode());

        Assertions.assertEquals(factory1, factory2);

    }

    /* 필기.
    *   엔티티 매니저
    *   엔티티 매니저는 엔티티를 저장하는 메모리 상의 데이터베이스를 관리하는 인스턴스이다.
    *   엔티티를 저장하고, 수정, 삭제, 조회하는 등의 엔티티와 관련된 모든 일을 한다.
    *   엔티티 매니저는 팩토리와 달리 thread-safe 하지 않기 때문에 동시성 문제가 발생할 수 있다.
    *   따라서 web 의 경우 일반적으로 request-scope 와 일치 시킨다.
    *  */

    @Test
    @DisplayName("엔티티 매니저 생성 확인")
    void testCreateManager() {

        EntityManager manager = EntityManagerGenerator.getInstance();

        System.out.println("manager 의 해쉬코드 : " + manager.hashCode());

        Assertions.assertNotNull(manager);
    }

    @Test
    @DisplayName("엔티티 매니저 스코프 확인")
    void testMangerLifeCycle() {

        // given
        EntityManager manager1 = EntityManagerGenerator.getInstance();
        // when
        EntityManager manager2 = EntityManagerGenerator.getInstance();
        // then
        System.out.println("manager1.hashCode() = " + manager1.hashCode());
        System.out.println("manager2.hashCode() = " + manager2.hashCode());

        Assertions.assertNotEquals(manager1, manager2);

    }

}
