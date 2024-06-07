package com.ohgiraffers.mapping.section02.embedded;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;

@Repository
public class BookRepository {

    @PersistenceContext
    private EntityManager manager;

    public void save(Book book) {

        manager.persist(book);

    }


}
