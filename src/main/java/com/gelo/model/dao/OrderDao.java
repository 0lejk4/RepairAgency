package com.gelo.model.dao;

import com.gelo.model.dao.generic.GenericDao;
import com.gelo.model.domain.Order;
import com.gelo.model.exception.DatabaseException;

import java.util.List;


/**
 * The interface Order dao.
 */
public interface OrderDao extends GenericDao<Order, Long> {

    /**
     * Find all orders.
     *
     * @return the list
     * @throws DatabaseException the database exception
     */
    List<Order> findAll() throws DatabaseException;

    /**
     * Find all orders by author using id.
     *
     * @param authorId   the author id
     * @param orderField the order field
     * @param ascending  the ascending
     * @param limit      the limit
     * @param offset     the offset
     * @return the list
     * @throws DatabaseException the database exception
     */
    List<Order> findAllByAuthor(Long authorId, String orderField, boolean ascending, int limit, int offset) throws DatabaseException;


    /**
     * Count by author long.
     *
     * @param authorId the author id
     * @return the long
     * @throws DatabaseException the database exception
     */
    Long countByAuthor(Long authorId) throws DatabaseException;

    /**
     * Find all orders by master using id.
     *
     * @param masterId   the master id
     * @param orderField the order field
     * @param ascending  the ascending
     * @param limit      the limit
     * @param offset     the offset
     * @return the list
     * @throws DatabaseException the database exception
     */
    List<Order> findAllByMaster(Long masterId, String orderField, boolean ascending, int limit, int offset) throws DatabaseException;


    /**
     * Count by master long.
     *
     * @param masterId the master id
     * @return the long
     * @throws DatabaseException the database exception
     */
    Long countByMaster(Long masterId) throws DatabaseException;

    /**
     * Find all orders by manager using id.
     *
     * @param managerId  the manager id
     * @param orderField the order field
     * @param ascending  the ascending
     * @param limit      the limit
     * @param offset     the offset
     * @return the list
     * @throws DatabaseException the database exception
     */
    List<Order> findAllByManager(Long managerId, String orderField, boolean ascending, int limit, int offset) throws DatabaseException;

    /**
     * Count by manager long.
     *
     * @param managerId the manager id
     * @return the long
     * @throws DatabaseException the database exception
     */
    Long countByManager(Long managerId) throws DatabaseException;

    /**
     * Find all awaiting answer orders.
     *
     * @param orderField the order field
     * @param ascending  the ascending
     * @param limit      the limit
     * @param offset     the offset
     * @return the list
     * @throws DatabaseException the database exception
     */
    List<Order> findAllAwaitingAnswer(String orderField, boolean ascending, int limit, int offset) throws DatabaseException;


    /**
     * Count awaiting answer long.
     *
     * @return the long
     * @throws DatabaseException the database exception
     */
    Long countAwaitingAnswer() throws DatabaseException;

    /**
     * Find all awaiting master orders.
     *
     * @param orderField the order field
     * @param ascending  the ascending
     * @param limit      the limit
     * @param offset     the offset
     * @return the list
     * @throws DatabaseException the database exception
     */
    List<Order> findAllAwaitingMaster(String orderField, boolean ascending, int limit, int offset) throws DatabaseException;

    /**
     * Count awaiting master long.
     *
     * @return the long
     * @throws DatabaseException the database exception
     */
    Long countAwaitingMaster() throws DatabaseException;

    /**
     * Find order by pk.
     *
     * @param orderId the order id
     * @return the order
     * @throws DatabaseException the database exception
     */
    Order findByPK(Long orderId) throws DatabaseException;


    /**
     * Update order.
     *
     * @param order the order
     * @throws DatabaseException the database exception
     */
    void update(Order order) throws DatabaseException;


    /**
     * Persist new order.
     *
     * @param order the order
     * @throws DatabaseException the database exception
     */
    void persist(Order order) throws DatabaseException;

}
