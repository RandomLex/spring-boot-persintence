package com.academy.it.boot.hierarchy.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;
    private String origin;
}
