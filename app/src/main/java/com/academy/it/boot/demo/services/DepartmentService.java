package com.academy.it.boot.demo.services;


import com.academy.it.boot.demo.model.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    List<Department> getAllDepartments();

    Optional<Department> getDepartment(Integer id);

    Department saveDepartment(Department employee);

    Optional<Department> deleteDepartment(Integer id);
}
