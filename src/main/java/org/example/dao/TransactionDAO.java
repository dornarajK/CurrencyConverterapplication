package org.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.datasource.JPAUtil;
import org.example.entity.Transaction;

public class TransactionDAO {

    public void save(Transaction transaction) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(transaction);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw new RuntimeException("Failed to save transaction", e);
        } finally {
            em.close();
        }
    }
}