package com.academy.it.boot.demo.repositories;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    List<T> findAll();
    Optional<T> find(Integer id);
    T save(T entity);
    Optional<T> remove(Integer id);
}
