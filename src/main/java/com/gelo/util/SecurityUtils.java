package com.gelo.util;

import com.gelo.model.domain.*;
import com.gelo.model.domain.Permission;
import com.gelo.model.domain.PermissionType;
import com.gelo.model.domain.RoleType;
import com.gelo.model.domain.User;

import java.util.Set;

/**
 * The type Security utils.
 */
public class SecurityUtils {
    /**
     * Has permission boolean.
     *
     * @param user           the user
     * @param permissionType the permission type
     * @return the boolean
     */
    public static boolean hasPermission(User user, PermissionType permissionType) {
        Set<Permission> permissions = user.getRole().getPermissions();

        if (permissionType == PermissionType.NONE) {
            return true;
        }

        for (Permission p : permissions) {
            if (p.getType().equals(permissionType)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Has role boolean.
     *
     * @param user     the user
     * @param roleType the role type
     * @return the boolean
     */
    public static boolean hasRole(User user, RoleType roleType) {
        if (roleType == RoleType.NONE) {
            return true;
        }

        return user.getRole().getType().equals(roleType);
    }
}
