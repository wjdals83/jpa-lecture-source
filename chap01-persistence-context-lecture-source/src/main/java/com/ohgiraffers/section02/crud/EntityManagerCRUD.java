package com.ohgiraffers.section02.crud;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
public class EntityManagerCRUD {

    private EntityManager manager;

    public EntityManager getManagerInstance() {

        return manager;
    }

    public Menu findMenuByMenuCode(int menuCode) {

        manager = EntityManagerGenerator.getManagerInstance();

        return manager.find(Menu.class, menuCode);  // 메뉴 클래스를 기반으로 메뉴코드로 값을 찾을거야
    }

    public Long saveAndReturnAllCount(Menu newMenu) {

        manager = EntityManagerGenerator.getManagerInstance();

        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        manager.persist(newMenu);  // application 수준에서 persistence context에 등록 요처하기
        manager.flush();    // 정보를ㄹ 쭉 밀어준다. persistenct context 에서 데이터베이스로 반영을 해준다.

        return getCount(manager);
    }

    private Long getCount(EntityManager manager) {

        return manager.createQuery("SELECT COUNT(*) FROM section02Menu", Long.class).getSingleResult();
    }

    public Menu modifyMenuName(int menuCode, String menuName) {

        Menu foundMenu = findMenuByMenuCode(menuCode);

        EntityTransaction transaction = manager.getTransaction();

        transaction.begin();

        foundMenu.setMenuName(menuName);

        manager.flush();

        return foundMenu;

    }

    public Long removeAndReturnAllCount(int menuCode) {

        Menu foundMenu = findMenuByMenuCode(menuCode);

        EntityTransaction transaction = manager.getTransaction();

        transaction.begin();

        manager.remove(foundMenu);
        manager.flush();

        return getCount(manager);

    }
}
