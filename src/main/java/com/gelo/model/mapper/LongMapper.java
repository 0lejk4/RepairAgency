package com.gelo.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Long mapper.
 */
public class LongMapper implements RowMapper<Long> {
    @Override
    public Long map(ResultSet rs) throws SQLException {
        return rs.getLong(1);
    }
}
