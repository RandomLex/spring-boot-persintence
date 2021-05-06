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

public class DepartmentRepositoryPostgres extends AbstractRepositoryPostgres<Department> implements DepartmentRepository {
    //language=SQL
    private static final String FIND_ALL_DEPARTMENTS =
            "SELECT d.id d_id, d.name d_name, c.id c_id, c.name c_name FROM department d " +
                    "join city c on c.id = d.city_id";
    //language=SQL
    private static final String WITH_ID = " where d.id = ?";
    private static final String FIND_ALL_DEPARTMENTS_WITH_ID = FIND_ALL_DEPARTMENTS + WITH_ID;
    //language=SQL
    private static final String INSERT_DEPARTMENT_SQL = "INSERT INTO department (name, city_id) " +
            " values (?, ?)";

    private static volatile DepartmentRepositoryPostgres instance;

    private DepartmentRepositoryPostgres() {
    }

    public static DepartmentRepositoryPostgres getInstance() {
        if (instance == null) {
            synchronized (DepartmentRepositoryPostgres.class) {
                if (instance == null) {
                    instance = new DepartmentRepositoryPostgres();
                }
            }
        }
        return instance;
    }


    @Override
    protected String getFindAllSql() {
        return FIND_ALL_DEPARTMENTS;
    }

    @Override
    protected String getFindOneSql() {
        return FIND_ALL_DEPARTMENTS_WITH_ID;
    }

    @Override
    protected List<Department> getItems(ResultSet rs) throws SQLException {
        Map<Integer, Department> departmentMap = new HashMap<>();
        Map<Integer, City> cityMap = new HashMap<>();
        while (rs.next()) {

            int dId = rs.getInt(D_ID);
            int cId = rs.getInt(C_ID);
            departmentMap.putIfAbsent(dId,
                    new Department()
                            .withId(dId)
                            .withName(rs.getString(D_NAME))
                            .withCity(putIfAbsentAndReturn(cityMap, cId,
                                    new City()
                                            .withId(cId)
                                            .withName(rs.getString(C_NAME)))));
            cityMap.computeIfPresent(cId, (id, city) ->
                    city.withDepartment(departmentMap.get(id)));
        }
        return new ArrayList<>(departmentMap.values());
    }

    @Override
    protected String getUpdateSql() {
        return null;
    }

    @Override
    protected void prepareForUpdate(Department entity, PreparedStatement ps) throws SQLException {

    }

    @Override
    protected String getInsertSql() {
        return INSERT_DEPARTMENT_SQL;
    }

    @Override
    protected void prepareForInsert(Department department, PreparedStatement ps) throws SQLException {
        ps.setString(1, department.getName());
        ps.setObject(2, department.getCity() == null ? null : department.getCity().getId());
    }

    @Override
    protected String getDeleteSql() {
        return null;
    }
}
