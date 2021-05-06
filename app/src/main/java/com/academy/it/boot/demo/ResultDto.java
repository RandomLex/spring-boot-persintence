package com.academy.it.boot.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto {
    private String name;
    private String type;
    private Integer cost;
}
