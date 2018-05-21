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
     * @param orderField the order field
     * @param ascending  the ascending
     * @param limit      the limit
     * @param offset     the offset
     * @return the list
     */
    List<Order> findAllAwaitingAnswer(String orderField, boolean ascending, int limit, int offset);

    /**
     * Count awaiting answer long.
     *
     * @return the long
     */
    Long countAwaitingAnswer();

    /**
     * Find all awaiting master list.
     *
     * @param orderField the order field
     * @param ascending  the ascending
     * @param limit      the limit
     * @param offset     the offset
     * @return the list
     */
    List<Order> findAllAwaitingMaster(String orderField, boolean ascending, int limit, int offset);

    /**
     * Count awaiting master long.
     *
     * @return the long
     */
    Long countAwaitingMaster();

    /**
     * Find all by author id list.
     *
     * @param authorId   the author id
     * @param orderField the order field
     * @param ascending  the ascending
     * @param limit      the limit
     * @param offset     the offset
     * @return the list
     */
    List<Order> findAllByAuthorId(Long authorId, String orderField, boolean ascending, int limit, int offset);

    /**
     * Count by author long.
     *
     * @param authorId the author id
     * @return the long
     */
    Long countByAuthor(Long authorId);

    /**
     * Find all by master id list.
     *
     * @param masterId   the master id
     * @param orderField the order field
     * @param ascending  the ascending
     * @param limit      the limit
     * @param offset     the offset
     * @return the list
     */
    List<Order> findAllByMasterId(Long masterId, String orderField, boolean ascending, int limit, int offset);

    /**
     * Count by master long.
     *
     * @param masterId the master id
     * @return the long
     */
    Long countByMaster(Long masterId);

    /**
     * Find all by manager id list.
     *
     * @param managerId  the manager id
     * @param orderField the order field
     * @param ascending  the ascending
     * @param limit      the limit
     * @param offset     the offset
     * @return the list
     */
    List<Order> findAllByManagerId(Long managerId, String orderField, boolean ascending, int limit, int offset);


    /**
     * Count by manager long.
     *
     * @param managerId the manager id
     * @return the long
     */
    Long countByManager(Long managerId);

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
