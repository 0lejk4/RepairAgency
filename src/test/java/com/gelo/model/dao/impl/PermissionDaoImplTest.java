package com.gelo.model.dao.impl;

import com.gelo.factory.DaoFactory;
import com.gelo.mock.entity.EntityMocks;
import com.gelo.model.dao.PermissionDao;
import com.gelo.model.domain.Permission;
import com.gelo.model.exception.DatabaseException;
import com.gelo.services.DataSource;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public class PermissionDaoImplTest {

    @Test
    public void getPermissionsByRoleId() throws SQLException, DatabaseException {
        Connection connection = DataSource.getInstance().getConnection();
        connection.setAutoCommit(false);

        PermissionDao permissionDao = DaoFactory.getPermissionDaoInstance();
        permissionDao.setConnection(connection);

        Set<Permission> permissionsByRoleId = permissionDao.getPermissionsByRoleId(1L);

        Set<Permission> expectedPermissionsByRoleId = EntityMocks.createUserPermissions();

        Assert.assertTrue("Test user permissions",permissionsByRoleId.containsAll(expectedPermissionsByRoleId));

        permissionsByRoleId = permissionDao.getPermissionsByRoleId(2L);

        expectedPermissionsByRoleId = EntityMocks.createManagerPermissions();

        Assert.assertTrue("Test manager permissions",permissionsByRoleId.containsAll(expectedPermissionsByRoleId));

        permissionsByRoleId = permissionDao.getPermissionsByRoleId(3L);

        expectedPermissionsByRoleId = EntityMocks.createMasterPermissions();

        Assert.assertTrue("Test master permissions",permissionsByRoleId.containsAll(expectedPermissionsByRoleId));

        permissionsByRoleId = permissionDao.getPermissionsByRoleId(4L);
        expectedPermissionsByRoleId = EntityMocks.createAdminPermissions();

        Assert.assertTrue("Test admin permissions",permissionsByRoleId.containsAll(expectedPermissionsByRoleId));
        DataSource.closeConnection(permissionDao.getConnection());
    }
}