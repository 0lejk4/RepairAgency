package com.gelo.services.impl;

import com.gelo.factory.DaoFactory;
import com.gelo.model.dao.OrderDao;
import com.gelo.model.dao.UserDao;
import com.gelo.model.domain.Order;
import com.gelo.model.domain.User;
import com.gelo.model.exception.DatabaseException;
import com.gelo.services.DataSource;
import com.gelo.services.OrderService;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

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
     *
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
     * Method that generify findAll action using different Callable for this purposes
     * @param callable - callable that returns the list of orders it is DAO call in general
     * @return - list of orders
     */
    private List<Order> findAllFromCallable(Callable<List<Order>> callable) {
        Connection conToUse = null;
        List<Order> orders;
        try {
            conToUse = DataSource.getInstance().getConnection();
            conToUse.setAutoCommit(false);
            orderDao.setConnection(conToUse);
            userDao.setConnection(conToUse);

            orders = callable.call();
            for (Order o : orders) {
                populateUserFields(o);
            }
            conToUse.commit();
        } catch (Exception e) {
            DbUtils.rollbackAndCloseQuietly(conToUse);
            orders = null;
        } finally {
            DataSource.closeConnection(conToUse);
        }

        return orders;
    }

    /**
     * Method that generify count action using different Callable for this purposes
     * @param callable - callable that returns count orders it is DAO call in general
     * @return - count of orders
     */
    private Long countFromCallable(Callable<Long> callable) {
        Connection conToUse = null;
        Long count = 0L;
        try {
            conToUse = DataSource.getInstance().getConnection();
            conToUse.setAutoCommit(false);
            orderDao.setConnection(conToUse);
            userDao.setConnection(conToUse);

            count = callable.call();

            conToUse.commit();
        } catch (Exception e) {
            DbUtils.rollbackAndCloseQuietly(conToUse);
        } finally {
            DataSource.closeConnection(conToUse);
        }

        return count;
    }

    @Override
    public List<Order> findAll() {
        return findAllFromCallable(() -> orderDao.findAll());
    }

    @Override
    public List<Order> findAllAwaitingAnswer(String orderField, boolean ascending, int limit, int offset) {
        return findAllFromCallable(() -> orderDao.findAllAwaitingAnswer(orderField, ascending, limit, offset));
    }

    @Override
    public Long countAwaitingAnswer() {
        return countFromCallable(() -> orderDao.countAwaitingAnswer());
    }

    @Override
    public List<Order> findAllAwaitingMaster(String orderField, boolean ascending, int limit, int offset) {
        return findAllFromCallable(() -> orderDao.findAllAwaitingMaster(orderField, ascending, limit, offset));
    }

    @Override
    public Long countAwaitingMaster() {
        return countFromCallable(() -> orderDao.countAwaitingMaster());
    }

    @Override
    public List<Order> findAllByAuthorId(Long authorId, String orderField, boolean ascending, int limit, int offset) {
        return findAllFromCallable(() -> orderDao.findAllByAuthor(authorId, orderField, ascending, limit, offset));
    }

    @Override
    public Long countByAuthor(Long authorId) {
        return countFromCallable(() -> orderDao.countByAuthor(authorId));
    }

    @Override
    public List<Order> findAllByMasterId(Long masterId, String orderField, boolean ascending, int limit, int offset) {
        return findAllFromCallable(() -> orderDao.findAllByMaster(masterId, orderField, ascending, limit, offset));
    }

    @Override
    public Long countByMaster(Long masterId) {
        return countFromCallable(() -> orderDao.countByMaster(masterId));
    }

    @Override
    public List<Order> findAllByManagerId(Long managerId, String orderField, boolean ascending, int limit, int offset) {
        return findAllFromCallable(() -> orderDao.findAllByManager(managerId, orderField, ascending, limit, offset));
    }

    @Override
    public Long countByManager(Long managerId) {
        return countFromCallable(() -> orderDao.countByManager(managerId));
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

            if (order.isAccepted()) {
                User manager = order.getManager();
                manager.setActiveOrdersCount(manager.getActiveOrdersCount() + 1);
                userDao.update(manager);
            } else {
                User author = order.getAuthor();
                author.setActiveOrdersCount(author.getActiveOrdersCount() - 1);
                userDao.update(author);
            }


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

            Order current = orderDao.findByPK(order.getId());

            current.setDone(order.isDone());
            populateUserFields(current);

            for (User u : Arrays.asList(current.getMaster(), current.getAuthor(), current.getManager())) {
                u.setActiveOrdersCount(u.getActiveOrdersCount() - 1);
                u.setSummaryOrdersCount(u.getSummaryOrdersCount() + 1);
                userDao.update(u);
            }


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
