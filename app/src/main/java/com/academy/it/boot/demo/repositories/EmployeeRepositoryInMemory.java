package com.academy.it.boot.demo.repositories;

import com.academy.it.boot.demo.model.Employee;
import org.springframework.stereotype.Component;

@Component("memory")
public class EmployeeRepositoryInMemory extends AbstractRepositoryInMemory<Employee> implements EmployeeRepository {
    {
        map.put(1, new Employee()
                .withId(1)
                .withName("Виктор")
                .withSalary(100));
        map.put(2, new Employee()
                .withId(2)
                .withName("Пётр")
                .withSalary(100));
        map.put(3, new Employee()
                .withId(3)
                .withName("Люда")
                .withSalary(80));
        map.put(4, new Employee()
                .withId(4)
                .withName("Аня")
                .withSalary(150));
        map.put(5, new Employee()
                .withId(5)
                .withName("Дима")
                .withSalary(120));
    }
}
