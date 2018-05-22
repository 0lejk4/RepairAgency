package com.gelo.services.impl;

import com.gelo.model.dao.OrderDao;
import com.gelo.model.dao.UserDao;
import com.gelo.model.domain.Order;
import com.gelo.model.domain.User;
import com.gelo.persistance.exception.DatabaseRuntimeException;
import com.gelo.persistance.exception.TransactionRuntimeException;
import com.gelo.persistance.transaction.TransactionManager;
import com.gelo.services.OrderService;
import com.gelo.util.BeanStorage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

/**
 * The type Order service.
 */
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);
    private OrderDao orderDao;
    private UserDao userDao;
    private TransactionManager transactionManager = BeanStorage.INSTANCE.get(TransactionManager.class);

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
     * Fetches user entities for input order
     *
     * @param o input order
     */
    private void populateUserFields(Order o) {
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

    @Override
    public List<Order> findAll() {
        Object[] result = new Object[1];

        try {
            transactionManager.tx(() -> {
                List<Order> orders = orderDao.findAll();
                for (Order o : orders) {
                    populateUserFields(o);
                }
                result[0] = orders;
            });
        } catch (DatabaseRuntimeException | TransactionRuntimeException e) {
            logger.error(e);
            return null;
        }
        return (List<Order>) result[0];

    }

    @Override
    public List<Order> findAllAwaitingAnswer(String orderField, boolean ascending, int limit, int offset) {
        Object[] result = new Object[1];
        try {
            transactionManager.tx(() -> {
                List<Order> orders = orderDao.findAllAwaitingAnswer(orderField, ascending, limit, offset);
                for (Order o : orders) {
                    populateUserFields(o);
                }
                result[0] = orders;
            });
        } catch (DatabaseRuntimeException | TransactionRuntimeException e) {
            logger.error(e);
            return null;
        }

        return (List<Order>) result[0];
    }

    @Override
    public Long countAwaitingAnswer() {
        try {
            return orderDao.countAwaitingAnswer();
        } catch (DatabaseRuntimeException e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public List<Order> findAllAwaitingMaster(String orderField, boolean ascending, int limit, int offset) {
        Object[] result = new Object[1];
        try {
            transactionManager.tx(() -> {
                List<Order> orders = orderDao.findAllAwaitingMaster(orderField, ascending, limit, offset);
                for (Order o : orders) {
                    populateUserFields(o);
                }
                result[0] = orders;
            });
        } catch (DatabaseRuntimeException | TransactionRuntimeException e) {
            logger.error(e);
            return null;
        }
        return (List<Order>) result[0];
    }

    @Override
    public Long countAwaitingMaster() {
        try {
            return orderDao.countAwaitingMaster();
        } catch (DatabaseRuntimeException | TransactionRuntimeException e) {
            logger.error(e);
            return 0L;
        }
    }

    @Override
    public List<Order> findAllByAuthorId(Long authorId, String orderField, boolean ascending, int limit, int offset) {
        Object[] result = new Object[1];
        try {
            transactionManager.tx(() -> {
                List<Order> orders = orderDao.findAllByAuthor(authorId, orderField, ascending, limit, offset);
                for (Order o : orders) {
                    populateUserFields(o);
                }
                result[0] = orders;
            });
        } catch (DatabaseRuntimeException | TransactionRuntimeException e) {
            logger.error(e);
            return null;
        }
        return (List<Order>) result[0];
    }

    @Override
    public Long countByAuthor(Long authorId) {
        try {
            return orderDao.countByAuthor(authorId);
        } catch (DatabaseRuntimeException | TransactionRuntimeException e) {
            logger.error(e);
            return 0L;
        }
    }

    @Override
    public List<Order> findAllByMasterId(Long masterId, String orderField, boolean ascending, int limit, int offset) {
        Object[] result = new Object[1];
        try {
            transactionManager.tx(() -> {
                List<Order> orders = orderDao.findAllByMaster(masterId, orderField, ascending, limit, offset);
                for (Order o : orders) {
                    populateUserFields(o);
                }
                result[0] = orders;
            });
        } catch (DatabaseRuntimeException | TransactionRuntimeException e) {
            logger.error(e);
            return null;
        }
        return (List<Order>) result[0];
    }

    @Override
    public Long countByMaster(Long masterId) {
        try {
            return orderDao.countByMaster(masterId);
        } catch (DatabaseRuntimeException e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public List<Order> findAllByManagerId(Long managerId, String orderField, boolean ascending, int limit, int offset) {
        Object[] result = new Object[1];
        try {
            transactionManager.tx(() -> {
                List<Order> orders = orderDao.findAllByManager(managerId, orderField, ascending, limit, offset);
                for (Order o : orders) {
                    populateUserFields(o);
                }
                result[0] = orders;
            });
        } catch (DatabaseRuntimeException | TransactionRuntimeException e) {
            logger.error(e);
            return null;
        }
        return (List<Order>) result[0];
    }

    @Override
    public Long countByManager(Long managerId) {
        try {
            return orderDao.countByManager(managerId);
        } catch (DatabaseRuntimeException | TransactionRuntimeException e) {
            logger.error(e);
            return 0L;
        }
    }

    @Override
    public boolean answerOrder(Order order) {
        try {
            transactionManager.tx(() -> {
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
            });
        } catch (DatabaseRuntimeException | TransactionRuntimeException e) {
            logger.error(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean linkMaster(Order order) {
        try {
            transactionManager.tx(() -> {
                User master = order.getMaster();
                master.setActiveOrdersCount(master.getActiveOrdersCount() + 1);
                userDao.update(master);

                Order current = orderDao.findByPK(order.getId());
                populateUserFields(current);
                current.setMaster(order.getMaster());

                orderDao.update(current);
            });
        } catch (DatabaseRuntimeException | TransactionRuntimeException e) {
            logger.error(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean saveOrder(Order order) {
        try {
            transactionManager.tx(() -> {
                User author = order.getAuthor();
                author.setActiveOrdersCount(author.getActiveOrdersCount() + 1);
                userDao.update(author);
                orderDao.persist(order);
            });
        } catch (DatabaseRuntimeException | TransactionRuntimeException e) {
            logger.error(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean finishOrder(Order order) {
        try {
            transactionManager.tx(() -> {
                Order current = orderDao.findByPK(order.getId());

                current.setDone(order.isDone());
                populateUserFields(current);

                for (User u : Arrays.asList(current.getMaster(), current.getAuthor(), current.getManager())) {
                    u.setActiveOrdersCount(u.getActiveOrdersCount() - 1);
                    u.setSummaryOrdersCount(u.getSummaryOrdersCount() + 1);
                    userDao.update(u);
                }

                orderDao.update(current);
            });
        } catch (DatabaseRuntimeException | TransactionRuntimeException e) {
            logger.error(e);
            return false;
        }
        return true;
    }
}
