package com.academy.it.boot.demo.repositories;


import com.academy.it.boot.demo.model.City;
import com.academy.it.boot.demo.model.Department;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityRepositoryPostgres extends AbstractRepositoryPostgres<City> implements CityRepository {
    //language=SQL
    private static final String FIND_ALL_CITIES = "SELECT c.id c_id, c.name c_name, d.id d_id, d.name d_name  FROM city c " +
            "join department d on c.id = d.city_id";
    //language=SQL
    private static final String WITH_ID = " where c.id = ?";
    private static final String FIND_CITY_WITH_ID = FIND_ALL_CITIES + WITH_ID;
    //language=SQL
    private static final String INSERT_CITY = "INSERT INTO city (name) VALUES (?)";

    private static volatile CityRepositoryPostgres instance;

    private CityRepositoryPostgres() {
    }

    public static CityRepositoryPostgres getInstance() {
        if (instance == null) {
            synchronized (CityRepositoryPostgres.class) {
                if (instance == null) {
                    instance = new CityRepositoryPostgres();
                }
            }
        }
        return instance;
    }


    @Override
    protected String getFindAllSql() {
        return FIND_ALL_CITIES;
    }

    @Override
    protected String getFindOneSql() {
        return FIND_CITY_WITH_ID;
    }

    @Override
    protected List<City> getItems(ResultSet rs) throws SQLException {
        Map<Integer, City> cityMap = new HashMap<>();
        Map<Integer, Department> departmentMap = new HashMap<>();
        while (rs.next()) {
            int cId = rs.getInt(C_ID);
            int dId = rs.getInt(D_ID);
            cityMap.putIfAbsent(cId,
                    new City()
                            .withId(cId)
                            .withName(rs.getString(C_NAME))
                            .withDepartment(putIfAbsentAndReturn(departmentMap, dId,
                                    new Department()
                                            .withId(dId)
                                            .withName(rs.getString(D_NAME)))));
            departmentMap.computeIfPresent(dId, (id, department) ->
                    department.withCity(cityMap.get(id)));
        }
        return new ArrayList<>(cityMap.values());
    }

    @Override
    protected String getUpdateSql() {
        return null;
    }

    @Override
    protected void prepareForUpdate(City entity, PreparedStatement ps) throws SQLException {

    }

    @Override
    protected String getInsertSql() {
        return INSERT_CITY;
    }

    @Override
    protected void prepareForInsert(City city, PreparedStatement ps) throws SQLException {
        ps.setString(1, city.getName());
    }

    @Override
    protected String getDeleteSql() {
        return null;
    }
}
