package com.gelo.model.dao.impl;

import com.gelo.mock.entity.EntityMocks;
import com.gelo.model.dao.UserDao;
import com.gelo.model.exception.DatabaseException;
import com.gelo.factory.DaoFactory;
import com.gelo.model.domain.User;
import com.gelo.services.DataSource;
import org.apache.commons.dbutils.DbUtils;
import org.junit.Assert;
import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImplTest {

    @Test
    public void findByEmail() throws DatabaseException, SQLException {
        Connection connection = DataSource.getInstance().getConnection();
        connection.setAutoCommit(false);

        UserDao userDao = DaoFactory.getUserDaoInstance();
        userDao.setConnection(connection);
        User user = userDao.findByEmail("odubinskiy@ukr.net");
        Assert.assertEquals("Олег Дубинський", user.getName());

        DbUtils.rollbackAndCloseQuietly(connection);
    }

    @Test(expected = DatabaseException.class)
    public void save_user_fails() throws DatabaseException, SQLException {
        Connection connection = DataSource.getInstance().getConnection();
        connection.setAutoCommit(false);

        UserDao userDao = DaoFactory.getUserDaoInstance();
        userDao.setConnection(connection);
        userDao.persist(new User.UserBuilder().id(1L).build());

        DbUtils.rollbackAndCloseQuietly(connection);
    }

    @Test(expected = DatabaseException.class)
    public void save_user_violates_email() throws SQLException, DatabaseException {
        Connection connection = DataSource.getInstance().getConnection();
        connection.setAutoCommit(false);

        UserDao userDao = DaoFactory.getUserDaoInstance();
        userDao.setConnection(connection);
        User test = EntityMocks.createTestUser2();
        test.setEmail("denis_parkhomenko@ukr.net");
        userDao.persist(test);

        DbUtils.rollbackAndCloseQuietly(connection);
    }

    @Test
    public void save_user_success() throws SQLException, DatabaseException {
        Connection connection = DataSource.getInstance().getConnection();
        connection.setAutoCommit(false);

        UserDao userDao = DaoFactory.getUserDaoInstance();
        userDao.setConnection(connection);
        User test = EntityMocks.createTestUser2();

        userDao.persist(test);

        User persisted = userDao.findByEmail(test.getEmail());

        Assert.assertEquals("User Name same",
                test.getName(), persisted.getName());
        Assert.assertEquals("User Country same", test.getCountry(), persisted.getCountry());

        DbUtils.rollbackAndCloseQuietly(userDao.getConnection());
    }

    @Test
    public void email_taken() throws DatabaseException, SQLException {
        Connection connection = DataSource.getInstance().getConnection();
        connection.setAutoCommit(false);

        UserDao userDao = DaoFactory.getUserDaoInstance();
        userDao.setConnection(connection);

        Assert.assertTrue(userDao.emailTaken("denis_parkhomenko@ukr.net"));

        Assert.assertFalse(userDao.emailTaken("iAmNot@Taken.com"));
        DbUtils.rollbackAndCloseQuietly(connection);
    }

    @Test
    public void get_by_id() throws DatabaseException, SQLException {
        Connection connection = DataSource.getInstance().getConnection();
        connection.setAutoCommit(false);

        UserDao userDao = DaoFactory.getUserDaoInstance();
        userDao.setConnection(connection);

        User user = userDao.findByPK(1L);

        Assert.assertNotNull(user);

        Assert.assertEquals(user.getEmail(), "business_kateruna1992@stukr.net");

        Assert.assertNull(userDao.findByPK(322L));

        DbUtils.rollbackAndCloseQuietly(connection);
    }


    @Test
    public void findFiveBestMasters() throws DatabaseException, SQLException {
        Connection connection = DataSource.getInstance().getConnection();
        connection.setAutoCommit(false);

        UserDao userDao = DaoFactory.getUserDaoInstance();
        userDao.setConnection(connection);

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

        DbUtils.rollbackAndCloseQuietly(userDao.getConnection());
    }

    @Test(expected = NotImplementedException.class)
    public void deleteException() throws DatabaseException, SQLException {
        Connection connection = DataSource.getInstance().getConnection();
        connection.setAutoCommit(false);

        UserDao userDao = DaoFactory.getUserDaoInstance();
        userDao.setConnection(connection);
        userDao.delete(new User.UserBuilder().build());


        DbUtils.rollbackAndCloseQuietly(userDao.getConnection());
    }
}
