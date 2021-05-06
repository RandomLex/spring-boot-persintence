package com.academy.it.boot.demo.services;


import com.academy.it.boot.demo.model.City;
import com.academy.it.boot.demo.model.Department;
import com.academy.it.boot.demo.repositories.CityRepository;
import com.academy.it.boot.demo.repositories.DepartmentRepository;
import com.academy.it.boot.demo.repositories.RepositoryFactory;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class DepartmentServiceImp implements DepartmentService {
    private final DepartmentRepository depRepository = RepositoryFactory.getDepartmentRepository();
    private final CityRepository cityRepository = RepositoryFactory.getCityRepository();

    @Override
    public List<Department> getAllDepartments() {
        return depRepository.findAll();
    }

    @Override
    public Optional<Department> getDepartment(Integer id) {
        return depRepository.find(id);
    }

    @Override
    public Department saveDepartment(Department department) {
        City city = department.getCity();
        if (city == null) {
            return depRepository.save(department);
        }
        City existingCity = cityRepository.findAll().stream()
                .filter(haveSameName(city))
                .findFirst()
                .orElseGet(() -> cityRepository.save(city.withDepartment(department)));
        return depRepository.save(department.withCity(existingCity));
    }

    private Predicate<City> haveSameName(City city) {
        return c -> c.getName().equals(city.getName());
    }

    @Override
    public Optional<Department> deleteDepartment(Integer id) {
        return depRepository.remove(id);
    }
}
