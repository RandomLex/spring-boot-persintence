package com.academy.it.boot.demo.repositories;


import com.academy.it.boot.demo.model.City;
import com.academy.it.boot.demo.model.Department;

public class DepartmentRepositoryInMemory extends AbstractRepositoryInMemory<Department> implements DepartmentRepository {
    private final CityRepository cityRepository = CityRepositoryInMemory.getInstance();

    private static volatile DepartmentRepositoryInMemory instance;
    private DepartmentRepositoryInMemory() {

    }

    public static DepartmentRepositoryInMemory getInstance() {
        if (instance == null) {
            synchronized (DepartmentRepositoryInMemory.class) {
                if (instance == null) {
                    instance = new DepartmentRepositoryInMemory();
                }
            }
        }
        return instance;
    }

    {
        City minsk = cityRepository.find(1).get();
        City gomel = cityRepository.find(2).get();
        Department travel = new Department()
                .withId(1)
                .withName("Перевозок")
                .withCity(minsk);
        Department financial = new Department()
                .withId(2)
                .withName("Финансов")
                .withCity(gomel);
        minsk.withDepartment(travel);
        gomel.withDepartment(financial);
        map.put(1, travel);
        map.put(2, financial);
    }
}
