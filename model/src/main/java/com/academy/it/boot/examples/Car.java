package com.academy.it.boot.examples;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@SequenceGenerator(name = "car_id_generator", sequenceName = "car_seq_id", initialValue = 1_000_000, allocationSize = 100)
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String model;

    @Temporal(TemporalType.DATE)
    @Column(name = "release_date")
    private Date releaseDate;

    @Enumerated(EnumType.STRING)
    private Engine engine;

    @Embedded
    private AudioSystem audioSystem;

}
