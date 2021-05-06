package com.academy.it.boot.demo.services;

import com.academy.it.boot.demo.model.Employee;
import com.academy.it.boot.demo.repositories.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository repository;

    private Map<String, EmployeeRepository> repositoryMap;
    @Value("${repository.type}")
    private String repositoryType;

    @PostConstruct
    private void init() {
        repository = repositoryMap.get(repositoryType);
    }

    @Autowired
    public void setRepositoryMap(Map<String, EmployeeRepository> repositoryMap) {
        this.repositoryMap = repositoryMap;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    @Override
    public Optional<Employee> getEmployee(Integer id) {
        return repository.find(id);
    }

    @Override
    public Employee saveEmployee(String name, int salary) {
        Employee employee = repository.save(new Employee()
                .withName(name)
                .withSalary(salary));
        log.info("A new Employee is added : {}", employee);
        return employee;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public Optional<Employee> deleteEmployee(Integer id) {
        return repository.remove(id);
    }
}
