package com.gelo.model.dao.impl;

import com.gelo.model.dao.PermissionDao;
import com.gelo.model.exception.DatabaseException;
import com.gelo.model.domain.Permission;
import com.gelo.model.domain.PermissionType;
import com.gelo.model.domain.Permission;
import com.gelo.model.domain.PermissionType;
import com.gelo.model.exception.DatabaseException;
import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Permission dao.
 */
public class PermissionDaoImpl extends ConnectibleImpl implements PermissionDao {
    private static final Logger logger = Logger.getLogger(PermissionDaoImpl.class);


    @Override
    public Set<Permission> getPermissionsByRoleId(Long id) throws DatabaseException {
        ResultSet rs = null;
        Set<Permission> permissions = new HashSet<>();
        try (PreparedStatement ps = getConnection()
                .prepareStatement("SELECT" +
                        "  permission_id," +
                        "  permissions.type permission_type" +
                        " FROM role_permission" +
                        "  INNER JOIN permissions ON permission_id = permissions.id" +
                        " WHERE role_id = ?;")) {
            ps.setLong(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                permissions.add(new Permission(rs.getLong("permission_id"),
                        PermissionType.valueOf(rs.getString("permission_type"))));
            }
        } catch (SQLException e) {
            logger.error("Error populating role with permissions where role id = " + id);
            throw new DatabaseException(
                    "%%% Exception occured in PermissionDao persist() %%% " + e);
        } finally {
            DbUtils.closeQuietly(rs);
        }


        return permissions;
    }
}
