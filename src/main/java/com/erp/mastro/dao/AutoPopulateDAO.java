package com.erp.mastro.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AutoPopulateDAO {

    @Autowired
    private EntityManagerFactory entityFactory;

    public <T> List<T> getAutoPopulateList(String propertyName, String searchKey, Class entityClass, int count) {

        List<T> entities = new ArrayList<>();
        EntityManager em = null;
        try {
            em = entityFactory.createEntityManager();
            em.getTransaction().begin();
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(entityClass);
            Root<T> root = criteria.from(entityClass);
            criteria.select(root).where(builder.like(root.get(propertyName), "%" + searchKey + "%"));
            Query query = em.createQuery(criteria);
            query.setFirstResult(0);
            query.setMaxResults(count);
            entities = query.getResultList();

            return entities;
        } finally {
            if (em != null) {
                em.getTransaction().commit();
                em.close();
            }
        }

    }
}
