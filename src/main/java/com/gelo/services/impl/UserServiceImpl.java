package com.gelo.services.impl;

import com.gelo.model.dao.PermissionDao;
import com.gelo.model.dao.UserDao;
import com.gelo.model.domain.User;
import com.gelo.persistance.exception.DatabaseRuntimeException;
import com.gelo.persistance.exception.TransactionRuntimeException;
import com.gelo.persistance.transaction.TransactionManager;
import com.gelo.services.UserService;
import com.gelo.util.BeanStorage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * The type User service.
 */
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private UserDao userDao;
    private PermissionDao permissionDao;
    private TransactionManager transactionManager = BeanStorage.INSTANCE.get(TransactionManager.class);

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


    @Override
    public List<User> findAll() {
        try {
            return userDao.findAll();
        } catch (DatabaseRuntimeException e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public List<User> findFiveBestMasters() {
        try {
            return userDao.findFiveBestMasters();
        } catch (DatabaseRuntimeException e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public boolean save(User user) {
        try {
            userDao.persist(user);
        } catch (DatabaseRuntimeException e) {
            logger.error(e);
            return false;
        }
        return true;
    }


    @Override
    public User findByEmail(String email) {
        final Object[] result = new Object[1];

        try {
            transactionManager.tx(() -> {
                User user = userDao.findByEmail(email);
                if (user == null) return;
                user.getRole().setPermissions(permissionDao.getPermissionsByRoleId(user.getRole().getId()));
                result[0] = user;
            });
        } catch (DatabaseRuntimeException | TransactionRuntimeException e) {
            logger.error(e);
            return null;
        }

        return (User) result[0];
    }


    @Override
    public boolean emailTaken(String email) {
        try {
            return userDao.emailTaken(email);
        } catch (DatabaseRuntimeException e) {
            logger.error(e);
            return true;
        }
    }

    @Override
    public User findById(Long id) {
        try {
            return userDao.findByPK(id);
        } catch (DatabaseRuntimeException e) {
            logger.error(e);
            return null;
        }
    }
}
