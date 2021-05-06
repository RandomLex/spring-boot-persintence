package com.academy.it.boot.demo.repositories;


import com.academy.it.boot.demo.model.City;

public class CityRepositoryInMemory extends AbstractRepositoryInMemory<City> implements CityRepository {

    private static volatile CityRepositoryInMemory instance;
    private CityRepositoryInMemory() {

    }

    public static CityRepositoryInMemory getInstance() {
        if (instance == null) {
            synchronized (CityRepositoryInMemory.class) {
                if (instance == null) {
                    instance = new CityRepositoryInMemory();
                }
            }
        }
        return instance;
    }

    {
        map.put(1, new City()
                .withId(1)
                .withName("Минск"));
        map.put(2, new City()
                .withId(2)
                .withName("Гомель"));
    }
}
