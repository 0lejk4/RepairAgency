package com.gelo.util;

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
     * Checks if user has input permission or if user is null returns false.
     * If it is NONE permission returns true immediately.
     *
     * @param user           the user
     * @param permissionType the permission type
     * @return the boolean
     */
    public static boolean hasPermission(User user, PermissionType permissionType) {
        if (permissionType == PermissionType.NONE) {
            return true;
        }

        if (user == null) {
            return false;
        }

        Set<Permission> permissions = user.getRole().getPermissions();

        for (Permission p : permissions) {
            if (p.getType().equals(permissionType)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Has role boolean.
     * Checks if user has input role or if user is null returns false.
     * If it is NONE role returns true immediately.
     *
     * @param user     the user
     * @param roleType the role type
     * @return the boolean
     */
    public static boolean hasRole(User user, RoleType roleType) {
        if (roleType == RoleType.NONE) {
            return true;
        }

        if (user == null) {
            return false;
        }

        return user.getRole().getType().equals(roleType);
    }
}
