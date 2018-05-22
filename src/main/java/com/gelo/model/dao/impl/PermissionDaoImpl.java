package com.gelo.model.dao.impl;

import com.gelo.model.dao.PermissionDao;
import com.gelo.model.domain.Permission;
import com.gelo.model.mapper.PermissionMapper;
import com.gelo.persistance.ConnectionManager;
import com.gelo.persistance.JdbcTemplate;

import java.util.HashSet;
import java.util.Set;

/**
 * The type Permission dao.
 */
public class PermissionDaoImpl implements PermissionDao {
    private final JdbcTemplate jdbcTemplate;
    private final PermissionMapper permissionMapper = new PermissionMapper();

    private static final String PERMISSION_FIND_BY_ROLE_ID = "SELECT  permission_id,  permissions.type permission_type FROM role_permission INNER JOIN permissions ON permission_id = permissions.id WHERE role_id = ?;";

    /**
     * Instantiates a new Permission dao.
     *
     * @param connectionManager the connection manager
     */
    public PermissionDaoImpl(ConnectionManager connectionManager) {
        jdbcTemplate = new JdbcTemplate(connectionManager);
    }

    @Override
    public Set<Permission> getPermissionsByRoleId(Long id) {
        return new HashSet<>(jdbcTemplate.queryObjects(PERMISSION_FIND_BY_ROLE_ID, permissionMapper, id));
    }
}
