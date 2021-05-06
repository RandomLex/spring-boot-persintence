package com.academy.it.boot.demo.services;


import com.academy.it.boot.demo.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    Optional<Employee> getEmployee(Integer id);

    Employee saveEmployee(String name, int salary);

    Employee saveEmployee(Employee employee);

    Optional<Employee> deleteEmployee(Integer id);
}
