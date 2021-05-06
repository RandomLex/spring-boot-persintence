package com.academy.it.boot.demo.repositories;

import com.academy.it.boot.demo.aspects.JpaTransaction;
import com.academy.it.boot.demo.model.AbstractEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public abstract class AbstractRepositoryJpa<T extends AbstractEntity> implements Repository<T> {
    private static final String ID = "id";
    protected EntityManagerHelper helper;

    protected abstract TypedQuery<T> findAllQuery();

    protected abstract TypedQuery<T> findOneQuery();

    @Autowired
    public void setHelper(EntityManagerHelper helper) {
        this.helper = helper;
    }

    @Override
    @JpaTransaction
    public List<T> findAll() {
        return findAllQuery().getResultList();
    }

    @Override
    public Optional<T> find(Integer id) {
        Optional<T> result;
        EntityManager em = helper.getEntityManager();
        EntityTransaction trx = em.getTransaction();
        trx.begin();
        result = Optional.ofNullable(findOneQuery()
                .setParameter(ID, id)
                .getSingleResult());
        trx.commit();
        em.close();
        return result;
    }

    @Override
    @JpaTransaction
    public T save(T entity) {
        EntityManager em = helper.getEntityManager();
        if (entity.getId() == null) {
            em.persist(entity);
        } else {
            em.merge(entity);
        }
        return entity;
    }

    @Override
    public Optional<T> remove(Integer id) {
        Optional<T> result;
        EntityManager em = helper.getEntityManager();
        em.getTransaction().begin();
        result = find(id);
        result.ifPresent(em::remove);
        em.getTransaction().commit();
        em.close();
        return result;
    }
}
