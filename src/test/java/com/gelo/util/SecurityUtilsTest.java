package com.gelo.util;

import com.gelo.mock.entity.EntityMocks;
import com.gelo.model.domain.PermissionType;
import com.gelo.model.domain.RoleType;
import org.junit.Assert;
import org.junit.Test;

public class SecurityUtilsTest {

    @Test
    public void test_hasRole() {
        Assert.assertFalse("Don`t have admin role", SecurityUtils.hasRole(EntityMocks.createTestUser(), RoleType.ROLE_ADMIN));

        Assert.assertTrue("Have user role", SecurityUtils.hasRole(EntityMocks.createTestUser(), RoleType.ROLE_USER));
    }

    @Test
    public void test_hasPermission() {
        Assert.assertTrue("Have create order permission",
                SecurityUtils.hasPermission(EntityMocks.createTestUser(), PermissionType.CREATE_ORDER));

        Assert.assertFalse("Don`t have finish order permission",
                SecurityUtils.hasPermission(EntityMocks.createTestUser(), PermissionType.FINISH_ORDER));

    }
}
