package com.academy.it.boot.demo.repositories;

import com.academy.it.boot.demo.model.City;
import com.academy.it.boot.demo.model.Department;
import com.academy.it.boot.demo.model.Employee;
import com.academy.it.boot.demo.model.Title;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class EmployeeRepositoryPostgres extends AbstractRepositoryPostgres<Employee> implements EmployeeRepository {
    private static final String SQL_EMPLOYEE_ALL_FIELDS = "select e.id e_id, e.name e_name, salary, " +
            "d.id d_id, d.name d_name, " +
            "c.id c_id, c.name c_name, " +
            "t.id t_id, t.name t_name " +
            "from employee e " +
            "left join title t " +
            "on t.id = e.title_id " +
            "left join department_employee de " +
            "on e.id = de.employee_id " +
            "left join department d " +
            "on d.id = de.department_id " +
            "left join city c " +
            "on d.city_id = c.id";
    //language=SQL
    private static final String ONE_EMPLOYEE_FILTER = " WHERE e.id = ?";
    private static final String FIND_EMPLOYEE_BY_ID = SQL_EMPLOYEE_ALL_FIELDS + ONE_EMPLOYEE_FILTER;
    //language=SQL
    private static final String UPDATE_EMPLOYEE_SET_NAME_SALARY_WHERE_ID = "UPDATE employee set name=?, salary=? WHERE id=?";
    //language=SQL
    protected static final String INSERT_INTO_EMPLOYEE_NAME_SALARY_VALUES_RETURNING_ID = "INSERT INTO employee (name, salary) VALUES (?, ?)";
    //language=SQL
    private static final String DELETE_FROM_EMPLOYEE_WHERE_ID = "DELETE FROM employee WHERE id=?";

    private static volatile EmployeeRepositoryPostgres instance;

    private EmployeeRepositoryPostgres() {
    }

    public static EmployeeRepositoryPostgres getInstance() {
        if (instance == null) {
            synchronized (EmployeeRepositoryPostgres.class) {
                if (instance == null) {
                    instance = new EmployeeRepositoryPostgres();
                }
            }
        }
        return instance;
    }

    @Override
    protected String getFindAllSql() {
        return SQL_EMPLOYEE_ALL_FIELDS;
    }

    @Override
    protected String getFindOneSql() {
        return FIND_EMPLOYEE_BY_ID;
    }

    @Override
    protected String getUpdateSql() {
        return UPDATE_EMPLOYEE_SET_NAME_SALARY_WHERE_ID;
    }

    @Override
    protected String getInsertSql() {
        return INSERT_INTO_EMPLOYEE_NAME_SALARY_VALUES_RETURNING_ID;
    }

    @Override
    protected String getDeleteSql() {
        return DELETE_FROM_EMPLOYEE_WHERE_ID;
    }

    @Override
    protected List<Employee> getItems(ResultSet rs) throws SQLException {
        Map<Integer, Employee> employeeMap = new LinkedHashMap<>();
        Map<Integer, Department> departmentMap = new HashMap<>();
        Map<Integer, City> cityMap = new HashMap<>();
        Map<Integer, Title> titleMap = new HashMap<>();

        while (rs.next()) {
            Integer tId = getInteger(rs, T_ID);
            Integer cId = getInteger(rs, C_ID);
            Integer dId = getInteger(rs, D_ID);
            Integer eId = getInteger(rs, E_ID);

            employeeMap.putIfAbsent(eId,
                    new Employee()
                            .withId(eId)
                            .withName(rs.getString(E_NAME))
                            .withSalary(rs.getInt(SALARY))
                            .withTitle(putIfAbsentAndReturn(titleMap, tId,
                                    new Title()
                                            .withId(tId)
                                            .withName(rs.getString(T_NAME))))
                            .withDepartment(putIfAbsentAndReturn(departmentMap, dId,
                                    new Department()
                                            .withId(dId)
                                            .withName(getRsString(rs, D_NAME))
                                            .withCity(putIfAbsentAndReturn(cityMap, cId,
                                                    new City()
                                                            .withId(cId)
                                                            .withName(getRsString(rs, C_NAME)))))));
            employeeMap.computeIfPresent(eId, (id, employee) ->
                    employee.withDepartment(departmentMap.get(dId)));
        }
        return new ArrayList<>(employeeMap.values());
    }

    protected void prepareForUpdate(Employee employee, PreparedStatement ps) throws SQLException {
        ps.setString(1, employee.getName());
        ps.setInt(2, employee.getSalary());
        ps.setInt(3, employee.getId());
    }

    protected void prepareForInsert(Employee employee, PreparedStatement ps) throws SQLException {
        ps.setString(1, employee.getName());
        ps.setInt(2, employee.getSalary());
    }

}
