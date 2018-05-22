package com.gelo.model.dao.impl;

import com.gelo.factory.DaoFactory;
import com.gelo.mock.entity.EntityMocks;
import com.gelo.model.dao.PermissionDao;
import com.gelo.model.domain.Permission;
import com.gelo.persistance.ConnectionManager;
import com.gelo.persistance.exception.DatabaseRuntimeException;
import com.gelo.persistance.util.ScriptRunner;
import com.gelo.util.ResourcesUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Set;

public class PermissionDaoImplTest {
    private DaoFactory daoFactory;

    @Before
    public void setUp() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager();
        daoFactory = new DaoFactory(connectionManager);
        ScriptRunner scriptRunner = new ScriptRunner(connectionManager);
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("SetupDB.sql"));
    }

    @Test
    public void getPermissionsByRoleId() throws SQLException, DatabaseRuntimeException {

        PermissionDao permissionDao = daoFactory.getPermissionDaoInstance();

        Set<Permission> permissionsByRoleId = permissionDao.getPermissionsByRoleId(1L);

        Set<Permission> expectedPermissionsByRoleId = EntityMocks.createUserPermissions();

        Assert.assertTrue("Test user permissions", permissionsByRoleId.containsAll(expectedPermissionsByRoleId));

        permissionsByRoleId = permissionDao.getPermissionsByRoleId(2L);

        expectedPermissionsByRoleId = EntityMocks.createManagerPermissions();

        Assert.assertTrue("Test manager permissions", permissionsByRoleId.containsAll(expectedPermissionsByRoleId));

        permissionsByRoleId = permissionDao.getPermissionsByRoleId(3L);

        expectedPermissionsByRoleId = EntityMocks.createMasterPermissions();

        Assert.assertTrue("Test master permissions", permissionsByRoleId.containsAll(expectedPermissionsByRoleId));

        permissionsByRoleId = permissionDao.getPermissionsByRoleId(4L);
        expectedPermissionsByRoleId = EntityMocks.createAdminPermissions();

        Assert.assertTrue("Test admin permissions", permissionsByRoleId.containsAll(expectedPermissionsByRoleId));
    }
}