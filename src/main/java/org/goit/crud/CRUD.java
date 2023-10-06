package org.goit.crud;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transaction;

public class CRUD {
    private final EntityManager entityManager;
    public CRUD(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public<T> void create(EntityManagerFactory factory, T ...entities){
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        for (T entity : entities) {
            manager.persist(entity);
        }
        transaction.commit();
        manager.close();
    }
}
