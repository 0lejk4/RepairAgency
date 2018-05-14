package com.gelo.services.impl;

import com.gelo.model.dao.OrderDao;
import com.gelo.model.dao.UserDao;
import com.gelo.model.domain.User;
import com.gelo.model.exception.DatabaseException;
import com.gelo.factory.DaoFactory;
import com.gelo.model.domain.Order;
import com.gelo.services.OrderService;
import com.gelo.services.DataSource;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * The type Order service.
 */
public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao;
    private UserDao userDao;

    /**
     * Instantiates a new Order service.
     *
     * @param orderDao the order dao
     * @param userDao  the user dao
     */
    public OrderServiceImpl(OrderDao orderDao, UserDao userDao) {
        this.orderDao = orderDao;
        this.userDao = userDao;
    }

    /**
     * Instantiates a new Order service.
     */
    public OrderServiceImpl() {
        this(DaoFactory.getOrderDaoInstance(), DaoFactory.getUserDaoInstance());
    }

    /**
     * Fetches user entities for input order
     * @param o input order
     * @throws DatabaseException exception that can occur while working with dao
     */
    private void populateUserFields(Order o) throws DatabaseException {
        o.setAuthor(
                userDao.findByPK(
                        o.getAuthor()
                                .getId())
        );
        o.setManager(
                userDao.findByPK(
                        o.getManager()
                                .getId())
        );
        o.setMaster(
                userDao.findByPK(
                        o.getMaster()
                                .getId())
        );
    }


    /**
     * Helper method to reduce boilerplate code
     * @param qualifier dao method to be called
     * @param number optional parameter need for some methods
     * @return orders list or empty list if qualifier does not exist
     * @throws DatabaseException exception that can occur while working with dao
     */
    private List<Order> useDaoByQualifierAndNumber(String qualifier, long number) throws DatabaseException {
        switch (qualifier) {
            case "ALL":
                return orderDao.findAll();
            case "AWAITING_ANSWER":
                return orderDao.findAllAwaitingAnswer();
            case "AWAITING_MASTER":
                return orderDao.findAllAwaitingMaster();
            case "BY_MASTER":
                return orderDao.findAllByMaster(number);
            case "BY_MANAGER":
                return orderDao.findAllByManager(number);
            case "BY_AUTHOR":
                return orderDao.findAllByAuthor(number);
            default:
                return Collections.emptyList();
        }
    }

    /**
     * Method that reduces boilerplate code using method
     * @param qualifier for calling method that decides what dao method to use
     * @param number for calling method that decides what dao method to use with this number as parameter
     * @return order list fetched using dao
     */
    private List<Order> getAllOrdersByQualifierAndNumber(String qualifier, long number) {
        Connection conToUse = null;
        List<Order> orders;
        try {
            conToUse = DataSource.getInstance().getConnection();
            conToUse.setAutoCommit(false);
            orderDao.setConnection(conToUse);
            userDao.setConnection(conToUse);
            orders = useDaoByQualifierAndNumber(qualifier, number);
            for (Order o : orders) {
                populateUserFields(o);
            }
            conToUse.commit();
        } catch (SQLException | DatabaseException e) {
            DbUtils.rollbackAndCloseQuietly(conToUse);
            orders = null;
        } finally {
            DataSource.closeConnection(conToUse);
        }

        return orders;
    }

    private List<Order> getAllOrdersByQualifier(String qualifier) {
        return getAllOrdersByQualifierAndNumber(qualifier, -1);
    }

    @Override
    public List<Order> findAll() {
        return getAllOrdersByQualifier("ALL");
    }

    @Override
    public List<Order> findAllAwaitingAnswer() {
        return getAllOrdersByQualifier("AWAITING_ANSWER");
    }

    @Override
    public List<Order> findAllAwaitingMaster() {
        return getAllOrdersByQualifier("AWAITING_MASTER");
    }

    @Override
    public List<Order> findAllByAuthorId(Long authorId) {
        return getAllOrdersByQualifierAndNumber("BY_AUTHOR", authorId);
    }

    @Override
    public List<Order> findAllByMasterId(Long masterId) {
        return getAllOrdersByQualifierAndNumber("BY_MASTER", masterId);
    }

    @Override
    public List<Order> findAllByManagerId(Long managerId) {
        return getAllOrdersByQualifierAndNumber("BY_MANAGER", managerId);
    }

    @Override
    public boolean answerOrder(Order order) {
        Connection conToUse = null;
        boolean status = true;
        try {
            conToUse = DataSource.getInstance().getConnection();
            conToUse.setAutoCommit(false);
            orderDao.setConnection(conToUse);
            userDao.setConnection(conToUse);
            User manager = order.getManager();
            manager.setActiveOrdersCount(manager.getActiveOrdersCount() + 1);
            userDao.update(manager);

            Order current = orderDao.findByPK(order.getId());
            populateUserFields(current);

            current.setPrice(order.getPrice());
            current.setManager(order.getManager());
            current.setAccepted(order.isAccepted());
            current.setManagerDescription(order.getManagerDescription());

            orderDao.update(current);
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
    public boolean linkMaster(Order order) {
        Connection conToUse = null;
        boolean status = true;
        try {
            conToUse = DataSource.getInstance().getConnection();
            conToUse.setAutoCommit(false);
            orderDao.setConnection(conToUse);
            userDao.setConnection(conToUse);
            User master = order.getMaster();
            master.setActiveOrdersCount(master.getActiveOrdersCount() + 1);
            userDao.update(master);

            Order current = orderDao.findByPK(order.getId());
            populateUserFields(current);
            current.setMaster(order.getMaster());

            orderDao.update(current);
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
    public boolean saveOrder(Order order) {
        Connection conToUse = null;
        boolean status = true;
        try {
            conToUse = DataSource.getInstance().getConnection();
            conToUse.setAutoCommit(false);
            orderDao.setConnection(conToUse);
            userDao.setConnection(conToUse);
            User author = order.getAuthor();
            author.setActiveOrdersCount(author.getActiveOrdersCount() + 1);
            userDao.update(author);
            orderDao.persist(order);
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
    public boolean finishOrder(Order order) {
        Connection conToUse = null;
        boolean status = true;
        try {
            conToUse = DataSource.getInstance().getConnection();
            conToUse.setAutoCommit(false);
            orderDao.setConnection(conToUse);
            userDao.setConnection(conToUse);

            User master = order.getMaster();
            master.setActiveOrdersCount(master.getActiveOrdersCount() - 1);
            master.setSummaryOrdersCount(master.getSummaryOrdersCount() + 1);

            userDao.update(master);

            Order current = orderDao.findByPK(order.getId());
            populateUserFields(current);

            current.setDone(order.isDone());
            User author = current.getAuthor();
            author.setActiveOrdersCount(author.getActiveOrdersCount() - 1);
            author.setSummaryOrdersCount(author.getSummaryOrdersCount() + 1);

            userDao.update(author);

            User manager = current.getManager();
            manager.setActiveOrdersCount(manager.getActiveOrdersCount() - 1);
            manager.setSummaryOrdersCount(manager.getSummaryOrdersCount() + 1);

            userDao.update(manager);

            orderDao.update(current);
            conToUse.commit();
        } catch (SQLException | DatabaseException e) {
            DbUtils.rollbackAndCloseQuietly(conToUse);
            status = false;
        } finally {
            DataSource.closeConnection(conToUse);
        }
        return status;
    }
}
