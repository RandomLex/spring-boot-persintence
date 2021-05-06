package com.academy.it.boot.demo.repositories;

import com.academy.it.boot.demo.model.AbstractEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractRepositoryInMemory<T extends AbstractEntity> implements Repository<T> {
    protected final Map<Integer, T> map = new ConcurrentHashMap<>();

    private static Integer generateId() {
        return ThreadLocalRandom.current().nextInt(1, 1_000);
    }

    public List<T> findAll() {
        if (map.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(map.values());
    }

    public Optional<T> find(Integer id) {
        return Optional.ofNullable(map.get(id));
    }

    public T save(T entity) {
        Integer id = entity.getId();
        if (id == null) {
            do {
                id = generateId();
            } while (map.containsKey(id));
            entity.setId(id);
        }
        map.put(id, entity);
        return map.get(id);
    }

    public Optional<T> remove(Integer id) {

        return Optional.ofNullable(map.remove(id));
    }
}
