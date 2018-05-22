package com.gelo.persistance;

import com.gelo.model.mapper.RowMapper;
import com.gelo.persistance.exception.DatabaseRuntimeException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Jdbc template.
 * Provides interface for common operations with ability to perform transactions.
 */
public class JdbcTemplate {

    private static final Logger LOGGER = LogManager.getLogger(JdbcTemplate.class);

    private ConnectionManager connectionManager;

    /**
     * Instantiates a new Jdbc template.
     *
     * @param connectionManager the connection manager
     */
    public JdbcTemplate(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    private void query(String query, ResultSetFunction fn, Object... params) {
        Connection conn = connectionManager.getConnection();

        if (conn == null)
            return;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            ResultSet rs = stmt.executeQuery();
            withRs(rs, fn);
        } catch (SQLException e) {
            LOGGER.warn("Cannot create prepared statement. Query: " + query, e);
            throw new DatabaseRuntimeException(e);
        } finally {
            tryClose(conn);
        }
    }

    /**
     * Query objects paginated list.
     * This creates unique pagination query and delegates other processing to queryObjects method
     *
     * @param <T>        the type parameter
     * @param query      the query
     * @param mapper     the mapper
     * @param orderField the order field
     * @param ascending  the ascending
     * @param limit      the limit
     * @param offset     the offset
     * @param params     the params
     * @return the list
     */
    public <T> List<T> queryObjectsPaginated(String query, RowMapper<T> mapper, String orderField, Boolean ascending, Integer limit, Integer offset, Object... params) {
        StringBuilder queryBuilder = new StringBuilder(query);

        if (ascending == null) {
            ascending = true;
        }
        if (orderField != null) {
            queryBuilder.append(" ORDER BY ").append(orderField).append(ascending ? " ASC " : " DESC ");
        }

        if (limit != null) {
            queryBuilder.append(" LIMIT ").append(limit);
        }

        if (offset != null) {
            queryBuilder.append(" OFFSET ").append(offset);
        }

        return queryObjects(queryBuilder.toString(), mapper, params);
    }

    /**
     * Query objects list.
     *
     * @param <T>    the type parameter
     * @param query  the query
     * @param mapper the mapper
     * @param params the params
     * @return the list
     */
    public <T> List<T> queryObjects(String query, RowMapper<T> mapper, Object... params) {
        List<T> entities = new ArrayList<>();

        query(query, rs -> {
            while (rs.next()) {
                entities.add(mapper.map(rs));
            }
        }, params);

        return entities;
    }

    /**
     * Query object t.
     *
     * @param <T>    the type parameter
     * @param query  the query
     * @param mapper the mapper
     * @param params the params
     * @return the t
     */
    public <T> T queryObject(String query, RowMapper<T> mapper, Object... params) {
        Object[] r = new Object[]{null};
        query(query, (rs) -> {
            if (rs.next()) {
                r[0] = mapper.map(rs);
            }
        }, params);

        return (T) r[0];
    }

    /**
     * Update int.
     *
     * @param updQuery the upd query
     * @param params   the params
     * @return the int
     */
    public int update(String updQuery, Object... params) {
        Connection conn = connectionManager.getConnection();

        if (conn == null)
            return 0;

        try (PreparedStatement stmt = conn.prepareStatement(updQuery)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e);
        } finally {
            tryClose(conn);
        }
    }

    /**
     * Insert long.
     *
     * @param updQuery the upd query
     * @param params   the params
     * @return the long
     */
    public Long insert(String updQuery, Object... params) {
        Connection conn = connectionManager.getConnection();

        if (conn == null)
            return null;

        try (PreparedStatement stmt
                     = conn.prepareStatement(updQuery, Statement.RETURN_GENERATED_KEYS)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                return rs.getLong(1);
            }

            return null;

        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e);
        } finally {
            tryClose(conn);
        }
    }

    private void withRs(ResultSet rs, ResultSetFunction fn) {
        try {
            fn.apply(rs);
        } catch (Exception e) {
            LOGGER.info("ResultSetFunctions has thrown an exception", e);
            throw new DatabaseRuntimeException(e);
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                LOGGER.error("Cannot tryClose ResultSet", e);
                throw new DatabaseRuntimeException(e);
            }
        }
    }


    private void tryClose(Connection connection) {
        connectionManager.closeConnection(connection);
    }

    /**
     * The interface Result set function.
     */
    @FunctionalInterface
    public interface ResultSetFunction {
        /**
         * Apply.
         *
         * @param resultSet the result set
         * @throws SQLException the sql exception
         */
        void apply(ResultSet resultSet) throws SQLException;
    }

}
