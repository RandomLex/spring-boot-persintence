package com.academy.it.boot.demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.LinkedHashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = "departments")
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class Employee extends AbstractEntity {
    private String name;
    @ManyToMany
    @JoinTable(
            name = "department_employee",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private Set<Department> departments = new LinkedHashSet<>();

    private Integer salary;
    @OneToOne
    private Title title;

    public Employee withId(Integer id) {
        setId(id);
        return this;
    }

    public Employee withName(String name) {
        setName(name);
        return this;
    }

    public Employee withDepartment(Department department) {
        if (department == null) {
            return this;
        }
        departments.add(department);
        return this;
    }

    public Employee withSalary(int salary) {
        setSalary(salary);
        return this;
    }

    public Employee withTitle(Title title) {
        setTitle(title);
        return this;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", departments=" + departments +
                ", salary=" + salary +
                ", title=" + title +
                '}';
    }
}
