package com.gelo.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Boolean mapper.
 */
public class BooleanMapper implements RowMapper<Boolean> {
    @Override
    public Boolean map(ResultSet rs) throws SQLException {
        return rs.getBoolean(1);
    }
}
