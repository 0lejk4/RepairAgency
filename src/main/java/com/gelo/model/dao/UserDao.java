package com.gelo.model.dao;

import com.gelo.model.dao.generic.GenericDao;
import com.gelo.model.exception.DatabaseException;
import com.gelo.model.domain.User;
import com.gelo.model.domain.User;
import com.gelo.model.exception.DatabaseException;

import java.util.List;

/**
 * The interface User dao.
 */
public interface UserDao extends GenericDao<User, Long> {
    /**
     * Persist.
     *
     * @param user the user
     * @throws DatabaseException the database exception
     */
    void persist(User user) throws DatabaseException;

    /**
     * Update.
     *
     * @param user the user
     * @throws DatabaseException the database exception
     */
    void update(User user) throws DatabaseException;

    /**
     * Find by email user.
     *
     * @param email the email
     * @return the user
     * @throws DatabaseException the database exception
     */
    User findByEmail(String email) throws DatabaseException;

    /**
     * Find by pk user.
     *
     * @param id the id
     * @return the user
     * @throws DatabaseException the database exception
     */
    User findByPK(Long id) throws DatabaseException;

    /**
     * Find all list.
     *
     * @return the list
     * @throws DatabaseException the database exception
     */
    List<User> findAll() throws DatabaseException;

    /**
     * Find five best masters list.
     *
     * @return the list
     * @throws DatabaseException the database exception
     */
    List<User> findFiveBestMasters() throws DatabaseException;

    /**
     * Email taken boolean.
     *
     * @param email the email
     * @return the boolean
     * @throws DatabaseException the database exception
     */
    boolean emailTaken(String email) throws DatabaseException;
}
