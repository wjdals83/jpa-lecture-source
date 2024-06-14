package com.ohgiraffers.jpql.section06.projection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectionRepository {

    @PersistenceContext
    private EntityManager manager;

    public List<Menu> singleEntityTest() {

        String jpql = "SELECT m FROM section06Menu m";

        List<Menu> menuList = manager.createQuery(jpql, Menu.class).getResultList();

        return menuList;
    }

    public Menu findMenu(int menuCode) {

        return manager.find(Menu.class, menuCode);

    }

    public BiDirectionCategory biDirectionProjection(int menuCode) {

        String jpql = "SELECT m.category FROM BiDirectionMenu m WHERE m.menuCode = :menuCode";
        BiDirectionCategory resultCategory = manager.createQuery(jpql, BiDirectionCategory.class)
                .setParameter("menuCode", menuCode)
                .getSingleResult();

        return resultCategory;
    }

    public List<MenuInfo> embeddedProjection() {

        String jpql = "SELECT m.menuInfo FROM EmbeddedMenu m";        // embeddabel 애들은 영속성 컨텍스트에서 관리하지 않기 떄문에 이것이 포함된 EmbeddeMenu 클래스에서 from 을 땡겨야 함.
        List<MenuInfo> resultMenuInfo = manager.createQuery(jpql, MenuInfo.class).getResultList();

        return resultMenuInfo;
    }

    public List<String> scalarTypedQuery() {

        String jpql = "SELECT c.categoryName FROM section06Category c";
        List<String> resultList = manager.createQuery(jpql, String.class).getResultList();

        return resultList;
    }

    public List<Object[]> scalarQuery() {

        String jpql = "SELECT c.categoryCode, c.categoryName FROM section06Category c";

        List<Object[]> resultList = manager.createQuery(jpql).getResultList();  //타입을 지정할 것이 없어 jpql 만 씀.

        return resultList;

    }

    public List<CategoryInfoDTO> newProjection() {

        String jpql = "SELECT new com.ohgiraffers.jpql.section06.projection.CategoryInfoDTO(c.categoryCode, c.categoryName) FROM section06Category c";
        List<CategoryInfoDTO> resultList = manager.createQuery(jpql, CategoryInfoDTO.class).getResultList();        // DTO 클래스를 자료형으로

        return resultList;
    }
}