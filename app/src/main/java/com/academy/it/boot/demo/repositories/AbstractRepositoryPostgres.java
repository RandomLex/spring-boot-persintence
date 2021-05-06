package com.academy.it.boot.demo.repositories;

import com.academy.it.boot.demo.exceptions.DatabaseException;
import com.academy.it.boot.demo.model.AbstractEntity;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public abstract class AbstractRepositoryPostgres<T extends AbstractEntity> implements Repository<T> {
    protected static final String ID = "id";
    protected static final String NAME = "name";
    protected static final String T_ID = "t_id";
    protected static final String C_ID = "c_id";
    protected static final String D_ID = "d_id";
    protected static final String E_ID = "e_id";
    protected static final String E_NAME = "e_name";
    protected static final String SALARY = "salary";
    protected static final String T_NAME = "t_name";
    protected static final String D_NAME = "d_name";
    protected static final String C_NAME = "c_name";

    private final DataSource dataSource = DataSource.getInstance();

    protected abstract String getFindAllSql();

    protected abstract String getFindOneSql();

    protected abstract List<T> getItems(ResultSet rs) throws SQLException;

    protected abstract String getUpdateSql();

    protected abstract void prepareForUpdate(T entity, PreparedStatement ps) throws SQLException;

    protected abstract String getInsertSql();

    protected abstract void prepareForInsert(T entity, PreparedStatement ps) throws SQLException;

    protected abstract String getDeleteSql();

    @Override
    public List<T> findAll() {
        List<T> result;
        String sql = getFindAllSql();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            result = getItems(rs);
        } catch (SQLException e) {
            log.error("error while retrieving", e);
            throw new DatabaseException(e);
        }
        return result;
    }

    @Override
    public Optional<T> find(Integer id) {
        String sql = getFindOneSql();
        List<T> result;
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            result = getItems(rs);
        } catch (SQLException e) {
            log.error("error while reading retrieving", e);
            throw new DatabaseException(e);
        }
        return result.stream().findFirst();
    }

    @Override
    public T save(T entity) {
        return entity.getId() == null ? insert(entity) : update(entity);
    }

    private T update(T entity) {
        String sql = getUpdateSql();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            prepareForUpdate(entity, ps);
            if (ps.executeUpdate() > 0) {
                return entity;
            }
            return null;
        } catch (SQLException e) {
            log.error("error while updating", e);
            throw new DatabaseException(e);
        }
    }

    private T insert(T entity) {
        String sql = getInsertSql();
        ResultSet rs = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql + " RETURNING id")) {
            prepareForInsert(entity, ps);
            rs = ps.executeQuery();
            if (rs.next()) {
                entity.setId(rs.getInt(ID));
                return entity;
            }
            return null;
        } catch (SQLException e) {
            log.error("error while inserting", e);
            throw new DatabaseException(e);
        } finally {
            close(rs);
        }
    }

    @Override
    public Optional<T> remove(Integer id) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(getDeleteSql())) {
            ps.setInt(1, id);
            Optional<T> entity = find(id);
            if (entity.isEmpty()) {
                return entity;
            }
            if (ps.executeUpdate() > 0) {
                return entity;
            }
            return Optional.empty();
        } catch (SQLException e) {
            log.error("error while removing", e);
            throw new DatabaseException(e);
        }
    }

    private String getItemName(List<T> result) {
        return result.stream().findAny().get().getClass().getName();
    }

    protected static Integer getInteger(ResultSet rs, String tId) throws SQLException {
        return rs.getObject(tId, Integer.class);
    }

    protected static <K, V> V putIfAbsentAndReturn(Map<K, V> map, K key, V value) {
        if (key == null) {
            return null;
        }
        map.putIfAbsent(key, value);
        return map.get(key);
    }

    protected String getRsString(ResultSet rs, String columnName) {
        try {
            return rs.getString(columnName);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    private Integer getRsInt(ResultSet rs, String columnName) {
        try {
            return rs.getInt(columnName);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    private void close(AutoCloseable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void rollbackWithLogging(Connection con, SQLException e) {
        log.error(e.getMessage(), e);
        try {
            if (con != null) {
                con.rollback();
            }
        } catch (SQLException e1) {
            log.error(e1.getMessage(), e1);
        }
    }
}
