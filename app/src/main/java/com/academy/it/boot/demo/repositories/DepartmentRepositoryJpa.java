package com.academy.it.boot.demo.repositories;


import com.academy.it.boot.demo.model.Department;

import javax.persistence.TypedQuery;

public class DepartmentRepositoryJpa extends AbstractRepositoryJpa<Department> implements DepartmentRepository {
    private static volatile DepartmentRepositoryJpa instance;

    private DepartmentRepositoryJpa() {
    }

    public static DepartmentRepositoryJpa getInstance() {
        if (instance == null) {
            synchronized (DepartmentRepositoryJpa.class) {
                if (instance == null) {
                    instance = new DepartmentRepositoryJpa();
                }
            }
        }
        return instance;
    }


    @Override
    protected TypedQuery<Department> findAllQuery() {
        return helper.getEntityManager()
                .createQuery("from Department", Department.class);
    }

    @Override
    protected TypedQuery<Department> findOneQuery() {
        return helper.getEntityManager()
                .createQuery("from Department where id = :id", Department.class);
    }
}
