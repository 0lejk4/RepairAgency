package com.gelo.model.dao.impl;

import com.gelo.factory.DaoFactory;
import com.gelo.mock.entity.EntityMocks;
import com.gelo.model.dao.OrderDao;
import com.gelo.model.domain.Order;
import com.gelo.persistance.ConnectionManager;
import com.gelo.persistance.exception.DatabaseRuntimeException;
import com.gelo.persistance.exception.TransactionRuntimeException;
import com.gelo.persistance.util.ScriptRunner;
import com.gelo.util.ResourcesUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.SQLException;
import java.util.List;

public class OrderDaoImplTest {

    private DaoFactory daoFactory;

    @Before
    public void setUp() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager();
        daoFactory = new DaoFactory(connectionManager);
        ScriptRunner scriptRunner = new ScriptRunner(connectionManager);
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("SetupDB.sql"));
    }

    private void testOrderPagination(List<Order> orders) {
        boolean isOrdered = true;

        for (int i = 0; i < orders.size() - 1; i++) {
            isOrdered = orders.get(i).getId() < orders.get(i + 1).getId();
        }

        Assert.assertTrue(isOrdered);

    }

    @Test
    public void findAllByAuthor() throws SQLException {
        OrderDao orderDao = daoFactory.getOrderDaoInstance();

        List<Order> orders = orderDao.findAllByAuthor(1L, "id", true, 2, 0);
        Assert.assertEquals(2, orders.size());
        testOrderPagination(orders);

    }

    @Test
    public void findAllByMaster() throws SQLException {
        OrderDao orderDao = daoFactory.getOrderDaoInstance();

        List<Order> orders = orderDao.findAllByMaster(5L, "id", true, 2, 0);
        Assert.assertEquals(2, orders.size());
        testOrderPagination(orders);

    }

    @Test
    public void findAllByManager() throws SQLException {
        OrderDao orderDao = daoFactory.getOrderDaoInstance();


        List<Order> orders = orderDao.findAllByManager(4L, "id", true, 2, 0);
        testOrderPagination(orders);
        Assert.assertEquals(2, orders.size());

    }

    @Test
    public void findAllAwaitingAnswer() throws SQLException {

        OrderDao orderDao = daoFactory.getOrderDaoInstance();

        List<Order> orders = orderDao.findAllAwaitingAnswer("id", true, 2, 0);
        Assert.assertEquals(2, orders.size());
        testOrderPagination(orders);
    }

    @Test
    public void findAllAwaitingMaster() throws SQLException {
        OrderDao orderDao = daoFactory.getOrderDaoInstance();


        List<Order> orders = orderDao.findAllAwaitingMaster("id", true, 2, 0);
        Assert.assertEquals(1, orders.size());
        testOrderPagination(orders);

    }

    @Test
    public void persist() throws SQLException {


        OrderDao orderDao = daoFactory.getOrderDaoInstance();

        orderDao.persist(EntityMocks.createTestOrder());
        Order order = orderDao.findByPK(11L);
        Assert.assertNotNull(order);
        Assert.assertNotNull(order.getAuthor());
        Assert.assertNotNull(order.getManager());
        Assert.assertNotNull(order.getMaster());
        Assert.assertEquals(11, order.getId().intValue());

    }

    @Test
    public void findByPK() throws SQLException {

        OrderDao orderDao = daoFactory.getOrderDaoInstance();


        Order order = orderDao.findByPK(1L);
        Assert.assertNotNull(order);
        Assert.assertNotNull(order.getAuthor());
        Assert.assertNotNull(order.getManager());
        Assert.assertNotNull(order.getMaster());
        Assert.assertEquals(1, order.getId().intValue());

    }

    @Test
    public void update() throws SQLException {
        OrderDao orderDao = daoFactory.getOrderDaoInstance();
        Order testOrder = EntityMocks.createTestOrder();
        testOrder.setMaster(EntityMocks.createTestMaster());
        orderDao.update(testOrder);
    }

    @Test
    public void delete() throws SQLException {
        OrderDao orderDao = daoFactory.getOrderDaoInstance();
        try {
            orderDao.delete(EntityMocks.createTestOrder());
            Assert.fail();
        } catch (DatabaseRuntimeException | TransactionRuntimeException | NotImplementedException e) {
        }
    }

    @Test
    public void findAll() throws SQLException {
        OrderDao orderDao = daoFactory.getOrderDaoInstance();

        List<Order> orders = orderDao.findAll();
        Assert.assertEquals(10, orders.size());
    }

}