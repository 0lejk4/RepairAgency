package com.gelo.model.mapper;

import com.gelo.model.domain.Permission;
import com.gelo.model.domain.PermissionType;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Permission mapper.
 */
public class PermissionMapper implements RowMapper<Permission> {
    @Override
    public Permission map(ResultSet rs) throws SQLException {
        return new Permission(rs.getLong("permission_id"),
                PermissionType.valueOf(rs.getString("permission_type")));
    }
}
