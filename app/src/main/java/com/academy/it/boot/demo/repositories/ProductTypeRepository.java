package com.academy.it.boot.demo.repositories;

import com.academy.it.boot.examples.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class ProductTypeRepository {

    EntityManagerHelper helper;

    @Autowired
    public void setHelper(EntityManagerHelper helper) {
        this.helper = helper;
    }

    public ProductType find(long id) {
        ProductType result = null;
        EntityManager em = helper.getEntityManager();
        em.getTransaction().begin();

        result = em.find(ProductType.class, id);

        em.getTransaction().commit();
        em.close();
        return result;
    }

}
