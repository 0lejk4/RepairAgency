package com.gelo.services.impl;

import com.gelo.model.dao.PermissionDao;
import com.gelo.model.dao.UserDao;
import com.gelo.model.exception.DatabaseException;
import com.gelo.factory.DaoFactory;
import com.gelo.model.domain.User;
import com.gelo.services.UserService;
import com.gelo.services.DataSource;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * The type User service.
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private PermissionDao permissionDao;

    /**
     * Instantiates a new User service.
     *
     * @param userDao       the user dao
     * @param permissionDao the permission dao
     */
    public UserServiceImpl(UserDao userDao, PermissionDao permissionDao) {
        this.userDao = userDao;
        this.permissionDao = permissionDao;
    }

    /**
     * Instantiates a new User service.
     */
    public UserServiceImpl() {
        this(DaoFactory.getUserDaoInstance(), DaoFactory.getPermissionDaoInstance());
    }

    @Override
    public List<User> findAll() {
        Connection connection = null;
        List<User> users = null;
        try {
            connection = DataSource.getInstance().getConnection();
            connection.setAutoCommit(false);
            userDao.setConnection(connection);
            users = userDao.findAll();
            connection.commit();
        } catch (SQLException | DatabaseException e) {
            DbUtils.rollbackAndCloseQuietly(connection);
        } finally {
            DataSource.closeConnection(connection);
        }

        return users;
    }

    @Override
    public List<User> findFiveBestMasters() {
        Connection connection = null;
        List<User> users = null;
        try {
            connection = DataSource.getInstance().getConnection();
            connection.setAutoCommit(false);
            userDao.setConnection(connection);
            users = userDao.findFiveBestMasters();
            connection.commit();
        } catch (SQLException | DatabaseException e) {
            DbUtils.rollbackAndCloseQuietly(connection);
        } finally {
            DataSource.closeConnection(connection);
        }

        return users;
    }

    @Override
    public boolean save(User user) {
        Connection conToUse = null;
        boolean status = true;
        try {
            conToUse = DataSource.getInstance().getConnection();
            conToUse.setAutoCommit(false);
            userDao.setConnection(conToUse);
            userDao.persist(user);
            conToUse.commit();
        } catch (SQLException | DatabaseException e) {
            DbUtils.rollbackAndCloseQuietly(conToUse);
            status = false;
        } finally {
            DataSource.closeConnection(conToUse);
        }

        return status;
    }


    @Override
    public User findByEmail(String email) {
        Connection conToUse = null;
        User user = null;
        try {
            conToUse = DataSource.getInstance().getConnection();
            conToUse.setAutoCommit(false);
            userDao.setConnection(conToUse);
            permissionDao.setConnection(conToUse);
            user = userDao.findByEmail(email);
            if (user == null) return null;
            user.getRole().setPermissions(permissionDao.getPermissionsByRoleId(user.getRole().getId()));
            conToUse.commit();
        } catch (SQLException | DatabaseException e) {
            DbUtils.rollbackAndCloseQuietly(conToUse);
        } finally {
            DataSource.closeConnection(conToUse);
        }

        return user;
    }


    @Override
    public boolean emailTaken(String email) {
        Connection conToUse = null;
        boolean taken;
        try {
            conToUse = DataSource.getInstance().getConnection();
            conToUse.setAutoCommit(false);
            userDao.setConnection(conToUse);
            taken = userDao.emailTaken(email);
            conToUse.commit();
        } catch (SQLException | DatabaseException e) {
            DbUtils.rollbackAndCloseQuietly(conToUse);
            taken = true;
        } finally {
            DataSource.closeConnection(conToUse);
        }

        return taken;
    }

    @Override
    public User findById(Long id) {
        Connection conToUse = null;
        User user = null;
        try {
            conToUse = DataSource.getInstance().getConnection();
            conToUse.setAutoCommit(false);
            userDao.setConnection(conToUse);
            user = userDao.findByPK(id);
            if (user == null) return null;
            conToUse.commit();
        } catch (SQLException | DatabaseException e) {
            DbUtils.rollbackAndCloseQuietly(conToUse);
        } finally {
            DataSource.closeConnection(conToUse);
        }

        return user;
    }
}
