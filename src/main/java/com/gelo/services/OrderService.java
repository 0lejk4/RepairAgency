package com.gelo.services;

import com.gelo.model.domain.Order;

import java.util.List;

/**
 * The interface Order service.
 */
public interface OrderService {
    /**
     * Find all list.
     *
     * @return the list
     */
    List<Order> findAll();

    /**
     * Find all awaiting answer list.
     *
     * @return the list
     */
    List<Order> findAllAwaitingAnswer();

    /**
     * Find all awaiting master list.
     *
     * @return the list
     */
    List<Order> findAllAwaitingMaster();

    /**
     * Find all by author id list.
     *
     * @param authorId the author id
     * @return the list
     */
    List<Order> findAllByAuthorId(Long authorId);

    /**
     * Find all by master id list.
     *
     * @param masterId the master id
     * @return the list
     */
    List<Order> findAllByMasterId(Long masterId);

    /**
     * Find all by manager id list.
     *
     * @param managerId the manager id
     * @return the list
     */
    List<Order> findAllByManagerId(Long managerId);

    /**
     * Answer order boolean.
     *
     * @param order the order
     * @return the boolean
     */
    boolean answerOrder(Order order);

    /**
     * Link master boolean.
     *
     * @param order the order
     * @return the boolean
     */
    boolean linkMaster(Order order);

    /**
     * Save order boolean.
     *
     * @param order the order
     * @return the boolean
     */
    boolean saveOrder(Order order);

    /**
     * Finish order boolean.
     *
     * @param order the order
     * @return the boolean
     */
    boolean finishOrder(Order order);

}
