package com.gelo.model.dao;

import com.gelo.model.domain.Permission;

import java.util.Set;

/**
 * The interface Permission dao.
 */
public interface PermissionDao {
    /**
     * Gets permissions by role id.
     * Return set of permissions for given role.
     *
     * @param id the id
     * @return the permissions by role id
     * @ the database exception
     */
    Set<Permission> getPermissionsByRoleId(Long id) ;
}
