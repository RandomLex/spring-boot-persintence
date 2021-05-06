package com.academy.it.boot.examples;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Report {
    @EmbeddedId
    private ReportKey id;

    private String recipient;
}
