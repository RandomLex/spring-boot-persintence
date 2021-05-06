package com.academy.it.boot.demo.repositories;


import com.academy.it.boot.demo.model.City;

import javax.persistence.TypedQuery;

public class CityRepositoryJpa extends AbstractRepositoryJpa<City> implements CityRepository {
    private static volatile CityRepositoryJpa instance;

    private CityRepositoryJpa() {
    }

    public static CityRepositoryJpa getInstance() {
        if (instance == null) {
            synchronized (CityRepositoryJpa.class) {
                if (instance == null) {
                    instance = new CityRepositoryJpa();
                }
            }
        }
        return instance;
    }

    @Override
    protected TypedQuery<City> findAllQuery() {
        return helper.getEntityManager()
                .createQuery("from City", City.class);
    }

    @Override
    protected TypedQuery<City> findOneQuery() {
        return helper.getEntityManager()
                .createQuery("from City where id = :id", City.class);
    }
}
