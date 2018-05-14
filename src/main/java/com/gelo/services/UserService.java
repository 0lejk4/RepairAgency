package com.gelo.services;

import com.gelo.model.domain.User;

import java.util.List;

/**
 * The interface User service.
 */
public interface UserService {
    /**
     * Find all list.
     *
     * @return the list
     */
    List<User> findAll();

    /**
     * Find five best masters list.
     *
     * @return the list
     */
    List<User> findFiveBestMasters();

    /**
     * Save boolean.
     *
     * @param user the user
     * @return the boolean
     */
    boolean save(User user);

    /**
     * Find by email user.
     *
     * @param email the email
     * @return the user
     */
    User findByEmail(String email);

    /**
     * Email taken boolean.
     *
     * @param email the email
     * @return the boolean
     */
    boolean emailTaken(String email);

    /**
     * Find by id user.
     *
     * @param id the id
     * @return the user
     */
    User findById(Long id);
}
