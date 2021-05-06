package com.academy.it.boot.demo.repositories;

import com.academy.it.boot.demo.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.TypedQuery;

@Slf4j
@Component("jpa")
public class EmployeeRepositoryJpa extends AbstractRepositoryJpa<Employee> implements EmployeeRepository {

    @Override
    protected TypedQuery<Employee> findAllQuery() {
        return helper.getEntityManager()
                .createQuery("from Employee", Employee.class);
    }

    @Override
    protected TypedQuery<Employee> findOneQuery() {
        return helper.getEntityManager()
                .createQuery("from Employee where id = :id", Employee.class);
    }
}
