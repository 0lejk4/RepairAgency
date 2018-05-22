package com.gelo.model.dao;

import com.gelo.model.dao.generic.GenericDao;
import com.gelo.model.domain.User;

import java.util.List;

/**
 * The interface User dao.
 */
public interface UserDao extends GenericDao<User, Long> {
    /**
     * Persist.
     *
     * @param user the user
     * @ the database exception
     */
    Long persist(User user) ;

    /**
     * Update.
     *
     * @param user the user
     * @ the database exception
     */
    void update(User user) ;

    /**
     * Find by email user.
     *
     * @param email the email
     * @return the user
     * @ the database exception
     */
    User findByEmail(String email) ;

    /**
     * Find by pk user.
     *
     * @param id the id
     * @return the user
     * @ the database exception
     */
    User findByPK(Long id) ;

    /**
     * Find all list.
     *
     * @return the list
     * @ the database exception
     */
    List<User> findAll() ;

    /**
     * Find five best masters list.
     *
     * @return the list
     * @ the database exception
     */
    List<User> findFiveBestMasters() ;

    /**
     * Email taken boolean.
     *
     * @param email the email
     * @return the boolean
     * @ the database exception
     */
    boolean emailTaken(String email) ;
}
