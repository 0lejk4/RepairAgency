package com.gelo.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * The interface Row mapper.
 * Class that implements it are used to parse result sets.
 *
 * @param <T> the type parameter
 */
@FunctionalInterface
public interface RowMapper<T> {

    /**
     * Map t.
     * Returns parsed from rs entity
     *
     * @param rs the rs
     * @return the t
     * @throws SQLException the sql exception
     */
    T map(ResultSet rs) throws SQLException;
}
