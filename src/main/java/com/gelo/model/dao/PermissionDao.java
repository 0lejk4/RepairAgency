package com.gelo.model.dao;

import com.gelo.model.exception.DatabaseException;
import com.gelo.model.domain.Permission;
import com.gelo.model.domain.Permission;
import com.gelo.model.exception.DatabaseException;

import java.util.Set;

/**
 * The interface Permission dao.
 */
public interface PermissionDao extends Connectible {
    /**
     * Gets permissions by role id.
     * Return set of permissions for given role.
     *
     * @param id the id
     * @return the permissions by role id
     * @throws DatabaseException the database exception
     */
    Set<Permission> getPermissionsByRoleId(Long id) throws DatabaseException;
}
