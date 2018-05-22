package com.gelo.model.dao.impl;

import com.gelo.factory.DaoFactory;
import com.gelo.mock.entity.EntityMocks;
import com.gelo.model.dao.UserDao;
import com.gelo.model.domain.User;
import com.gelo.persistance.ConnectionManager;
import com.gelo.persistance.exception.DatabaseRuntimeException;
import com.gelo.persistance.util.ScriptRunner;
import com.gelo.util.ResourcesUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.SQLException;
import java.util.List;

public class UserDaoImplTest {
    private DaoFactory daoFactory;

    @Before
    public void setUp() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager();
        daoFactory = new DaoFactory(connectionManager);
        ScriptRunner scriptRunner = new ScriptRunner(connectionManager);
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("SetupDB.sql"));
    }

    @Test
    public void findByEmail() throws DatabaseRuntimeException, SQLException {
        UserDao userDao = daoFactory.getUserDaoInstance();
        User user = userDao.findByEmail("odubinskiy@ukr.net");
        Assert.assertEquals("Олег Дубинський", user.getName());

    }

    @Test(expected = DatabaseRuntimeException.class)
    public void save_user_fails() throws DatabaseRuntimeException, SQLException {
        UserDao userDao = daoFactory.getUserDaoInstance();
        userDao.persist(new User.UserBuilder().id(1L).build());
    }

    @Test(expected = DatabaseRuntimeException.class)
    public void save_user_violates_email() throws SQLException, DatabaseRuntimeException {
        UserDao userDao = daoFactory.getUserDaoInstance();
        User test = EntityMocks.createTestUser2();
        test.setEmail("denis_parkhomenko@ukr.net");
        userDao.persist(test);
    }

    @Test
    public void save_user_success() throws SQLException, DatabaseRuntimeException {

        UserDao userDao = daoFactory.getUserDaoInstance();

        User test = EntityMocks.createTestUser2();

        userDao.persist(test);

        User persisted = userDao.findByEmail(test.getEmail());

        Assert.assertEquals("User Name same",
                test.getName(), persisted.getName());
        Assert.assertEquals("User Country same", test.getCountry(), persisted.getCountry());


    }

    @Test
    public void email_taken() throws DatabaseRuntimeException, SQLException {
        UserDao userDao = daoFactory.getUserDaoInstance();

        Assert.assertTrue(userDao.emailTaken("denis_parkhomenko@ukr.net"));

        Assert.assertFalse(userDao.emailTaken("iAmNot@Taken.com"));
    }

    @Test
    public void get_by_id() throws DatabaseRuntimeException, SQLException {
        UserDao userDao = daoFactory.getUserDaoInstance();

        User user = userDao.findByPK(1L);

        Assert.assertNotNull(user);

        Assert.assertEquals(user.getEmail(), "business_kateruna1992@stukr.net");

        Assert.assertNull(userDao.findByPK(322L));

    }


    @Test
    public void findFiveBestMasters() throws DatabaseRuntimeException, SQLException {
        UserDao userDao = daoFactory.getUserDaoInstance();

        List<User> fiveBestMasters = userDao.findFiveBestMasters();

        Assert.assertEquals(fiveBestMasters.size(), 5);
        boolean rightOrder = true;

        for (int i = 0; i < fiveBestMasters.size() - 1; i++) {
            if (fiveBestMasters.get(i).getSummaryOrdersCount() < fiveBestMasters.get(i + 1).getSummaryOrdersCount()) {
                rightOrder = false;
                break;
            }
        }

        Assert.assertTrue(rightOrder);

    }

    @Test(expected = NotImplementedException.class)
    public void deleteException() throws DatabaseRuntimeException, SQLException {

        UserDao userDao = daoFactory.getUserDaoInstance();
        userDao.delete(new User.UserBuilder().build());

    }
}
