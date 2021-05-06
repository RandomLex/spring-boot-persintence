package com.academy.it.boot.examples;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@ToString(exclude = "productType")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@NamedQueries(@NamedQuery(name = "byName", query = "select p from Product p where name = :name"))
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private int cost;

    public Product(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "product_type_id")
    private ProductType productType;



}
