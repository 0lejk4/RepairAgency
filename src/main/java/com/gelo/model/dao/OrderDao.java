package com.gelo.model.dao;

import com.gelo.model.dao.generic.GenericDao;
import com.gelo.model.exception.DatabaseException;
import com.gelo.model.domain.Order;
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
     * @param authorId the author id
     * @return the list
     * @throws DatabaseException the database exception
     */
    List<Order> findAllByAuthor(Long authorId) throws DatabaseException;

    /**
     * Find all orders by master using id.
     *
     * @param masterId the master id
     * @return the list
     * @throws DatabaseException the database exception
     */
    List<Order> findAllByMaster(Long masterId) throws DatabaseException;


    /**
     * Find all orders by manager using id.
     *
     * @param managerId the manager id
     * @return the list
     * @throws DatabaseException the database exception
     */
    List<Order> findAllByManager(Long managerId) throws DatabaseException;


    /**
     * Find all awaiting answer orders.
     *
     * @return the list
     * @throws DatabaseException the database exception
     */
    List<Order> findAllAwaitingAnswer() throws DatabaseException;

    /**
     * Find all awaiting master orders.
     *
     * @return the list
     * @throws DatabaseException the database exception
     */
    List<Order> findAllAwaitingMaster() throws DatabaseException;


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
