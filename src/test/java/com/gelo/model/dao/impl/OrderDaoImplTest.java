package com.gelo.model.dao.impl;

import com.gelo.factory.DaoFactory;
import com.gelo.mock.entity.EntityMocks;
import com.gelo.model.dao.OrderDao;
import com.gelo.model.domain.Order;
import com.gelo.model.exception.DatabaseException;
import com.gelo.services.DataSource;
import org.apache.commons.dbutils.DbUtils;
import org.junit.Assert;
import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderDaoImplTest {

    private void testOrderPagination(List<Order> orders) {
        boolean isOrdered = true;

        for (int i = 0; i < orders.size() - 1; i++) {
            isOrdered = orders.get(i).getId() < orders.get(i + 1).getId();
        }

        Assert.assertTrue(isOrdered);

    }

    @Test
    public void findAllByAuthor() throws SQLException {
        Connection connection = DataSource.getInstance().getConnection();
        connection.setAutoCommit(false);

        OrderDao orderDao = DaoFactory.getOrderDaoInstance();

        orderDao.setConnection(connection);

        try {
            List<Order> orders = orderDao.findAllByAuthor(1L, "id", true, 2, 0);
            Assert.assertEquals(2, orders.size());
            testOrderPagination(orders);

        } catch (DatabaseException e) {
        } finally {
            DbUtils.rollbackAndCloseQuietly(connection);
        }
    }

    @Test
    public void findAllByMaster() throws SQLException {
        Connection connection = DataSource.getInstance().getConnection();
        connection.setAutoCommit(false);

        OrderDao orderDao = DaoFactory.getOrderDaoInstance();

        orderDao.setConnection(connection);

        try {
            List<Order> orders = orderDao.findAllByMaster(5L, "id", true, 2, 0);
            Assert.assertEquals(2, orders.size());
            testOrderPagination(orders);
        } catch (DatabaseException e) {
        } finally {
            DbUtils.rollbackAndCloseQuietly(connection);
        }
    }

    @Test
    public void findAllByManager() throws SQLException {
        Connection connection = DataSource.getInstance().getConnection();
        connection.setAutoCommit(false);

        OrderDao orderDao = DaoFactory.getOrderDaoInstance();

        orderDao.setConnection(connection);

        try {
            List<Order> orders = orderDao.findAllByManager(4L, "id", true, 2, 0);
            testOrderPagination(orders);
            Assert.assertEquals(2, orders.size());
        } catch (DatabaseException e) {
        } finally {
            DbUtils.rollbackAndCloseQuietly(connection);
        }
    }

    @Test
    public void findAllAwaitingAnswer() throws SQLException {
        Connection connection = DataSource.getInstance().getConnection();
        connection.setAutoCommit(false);

        OrderDao orderDao = DaoFactory.getOrderDaoInstance();

        orderDao.setConnection(connection);

        try {
            List<Order> orders = orderDao.findAllAwaitingAnswer("id", true, 2, 0);
            Assert.assertEquals(2, orders.size());
            testOrderPagination(orders);
        } catch (DatabaseException e) {
        } finally {
            DbUtils.rollbackAndCloseQuietly(connection);
        }
    }

    @Test
    public void findAllAwaitingMaster() throws SQLException {
        Connection connection = DataSource.getInstance().getConnection();
        connection.setAutoCommit(false);

        OrderDao orderDao = DaoFactory.getOrderDaoInstance();

        orderDao.setConnection(connection);

        try {
            List<Order> orders = orderDao.findAllAwaitingMaster("id", true, 2, 0);
            Assert.assertEquals(1, orders.size());
            testOrderPagination(orders);
        } catch (DatabaseException e) {
        } finally {
            DbUtils.rollbackAndCloseQuietly(connection);
        }
    }

    @Test
    public void persist() throws SQLException {
        Connection connection = DataSource.getInstance().getConnection();
        connection.setAutoCommit(false);

        OrderDao orderDao = DaoFactory.getOrderDaoInstance();

        orderDao.setConnection(connection);

        try {
            orderDao.persist(EntityMocks.createTestOrder());
            Order order = orderDao.findByPK(11L);
            Assert.assertNotNull(order);
            Assert.assertNotNull(order.getAuthor());
            Assert.assertNotNull(order.getManager());
            Assert.assertNotNull(order.getMaster());
            Assert.assertEquals(11, order.getId().intValue());
        } catch (DatabaseException e) {
            e.printStackTrace();
        } finally {
            DbUtils.rollbackAndCloseQuietly(connection);
        }
    }

    @Test
    public void findByPK() throws SQLException {
        Connection connection = DataSource.getInstance().getConnection();
        connection.setAutoCommit(false);

        OrderDao orderDao = DaoFactory.getOrderDaoInstance();

        orderDao.setConnection(connection);

        try {
            Order order = orderDao.findByPK(1L);
            Assert.assertNotNull(order);
            Assert.assertNotNull(order.getAuthor());
            Assert.assertNotNull(order.getManager());
            Assert.assertNotNull(order.getMaster());
            Assert.assertEquals(1, order.getId().intValue());
        } catch (DatabaseException e) {
            e.printStackTrace();
        } finally {
            DbUtils.rollbackAndCloseQuietly(connection);
        }
    }

    @Test
    public void update() throws SQLException {
        Connection connection = DataSource.getInstance().getConnection();
        connection.setAutoCommit(false);

        OrderDao orderDao = DaoFactory.getOrderDaoInstance();

        orderDao.setConnection(connection);

        try {
            orderDao.update(EntityMocks.createTestOrder());
            Assert.fail();
        } catch (DatabaseException e) {
        } finally {
            DbUtils.rollbackAndCloseQuietly(connection);
        }
    }

    @Test
    public void delete() throws SQLException {
        Connection connection = DataSource.getInstance().getConnection();
        connection.setAutoCommit(false);

        OrderDao orderDao = DaoFactory.getOrderDaoInstance();

        orderDao.setConnection(connection);

        try {
            orderDao.delete(EntityMocks.createTestOrder());
            Assert.fail();
        } catch (DatabaseException | NotImplementedException e) {
        } finally {
            DbUtils.rollbackAndCloseQuietly(connection);
        }

    }

    @Test
    public void findAll() throws SQLException {
        Connection connection = DataSource.getInstance().getConnection();
        connection.setAutoCommit(false);

        OrderDao orderDao = DaoFactory.getOrderDaoInstance();

        orderDao.setConnection(connection);

        try {
            List<Order> orders = orderDao.findAll();
            Assert.assertEquals(10, orders.size());

        } catch (DatabaseException e) {
        } finally {
            DbUtils.rollbackAndCloseQuietly(connection);
        }
    }

}