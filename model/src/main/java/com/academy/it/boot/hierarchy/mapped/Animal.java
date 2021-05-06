package com.academy.it.boot.hierarchy.mapped;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@MappedSuperclass
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String origin;
}
