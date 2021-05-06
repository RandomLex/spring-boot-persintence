package com.academy.it.boot.demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class Department extends AbstractEntity {
    private String name;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;


    @ManyToMany(mappedBy = "departments", cascade = CascadeType.ALL)
    private Set<Employee> employees = new HashSet<>();

    public Department withId(Integer id) {
        setId(id);
        return this;
    }

    public Department withName(String name) {
        setName(name);
        return this;
    }

    public Department withCity(City city) {
        setCity(city);
        return this;
    }

    public Department withEmployee(Employee employee) {
        employees.add(employee);
        return this;
    }

    @Override
    public String toString() {
        return "Department{" +
                "name='" + name + '\'' +
                ", city=" + city +
//                ", employees=" + employees +
                '}';
    }
}
