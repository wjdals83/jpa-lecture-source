package com.ohgiraffers.jpql.section04.groupfunction;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupRepository {

    @PersistenceContext
    private EntityManager manager;


    public long countMenuOfCategory(int categoryCode) {

        String jpql = "SELECT COUNT(m.menuPrice) FROM section04Menu m WHERE m.categoryCode = :categoryCode";
        long countOfMenu = manager.createQuery(jpql, Long.class)
                .setParameter("categoryCode", categoryCode)
                .getSingleResult();

        return countOfMenu;

    }

    public Long noResult(int categoryCode) {

        String jpql = "SELECT SUM(m.menuPrice) FROM section04Menu m WHERE m.categoryCode = :categoryCode";
//        long sumOfPrice = manager.createQuery(jpql, Long.class)
//                .setParameter("categoryCode", categoryCode)
//                .getSingleResult();

        Long sumOfPrice = manager.createQuery(jpql, Long.class)
                .setParameter("categoryCode", categoryCode)
                .getSingleResult();

        return sumOfPrice;

    }

    public List<Object[]> selectGroupAndHaving(long minPrice) {

        String jpql = "SELECT m.categoryCode, SUM(m.menuPrice)" +
                " FROM section04Menu m" +
                " GROUP BY m.categoryCode" +
                " HAVING SUM(m.menuPrice) >= :minPrice";

        List<Object[]> sumPriceCategoryList = manager.createQuery(jpql)
                .setParameter("minPrice", minPrice)
                .getResultList();

        return sumPriceCategoryList;

    }
}
