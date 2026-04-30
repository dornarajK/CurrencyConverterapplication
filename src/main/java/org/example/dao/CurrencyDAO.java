package org.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.datasource.JPAUtil;
import org.example.entity.Currency;
import java.util.List;

public class CurrencyDAO {

    public List<Currency> getAllCurrencies() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createQuery("SELECT c FROM Currency c", Currency.class)
                    .getResultList();
        }
    }

    public double getExchangeRate(String abbreviation) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            Currency c = em.find(Currency.class, abbreviation);
            if (c == null) {
                throw new RuntimeException("Currency not found: " + abbreviation);
            }
            return c.getExchangeRateToUSD();
        }
    }

    public void insert(Currency currency) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = null;
        try (em) {
            tx = em.getTransaction();
            tx.begin();
            em.persist(currency);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw new RuntimeException("Failed to insert currency", e);
        }
    }
}