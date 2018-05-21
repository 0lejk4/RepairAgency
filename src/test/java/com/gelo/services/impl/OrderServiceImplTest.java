package com.gelo.services.impl;

import com.gelo.mock.entity.EntityMocks;
import com.gelo.model.dao.OrderDao;
import com.gelo.model.dao.UserDao;
import com.gelo.model.domain.Order;
import com.gelo.model.domain.User;
import com.gelo.model.exception.DatabaseException;
import com.gelo.services.OrderService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Collections;
import java.util.List;

public class OrderServiceImplTest {
    @Mock
    private UserDao userDao;

    @Mock
    private OrderDao orderDao;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void findAll() throws DatabaseException {
        OrderService orderService = new OrderServiceImpl(orderDao, userDao);

        Order testOrder = EntityMocks.createTestOrder();
        List<Order> singleOrder = Collections.singletonList(testOrder);
        Mockito.when(orderDao.findAll()).thenReturn(singleOrder);
        Mockito.when(userDao.findByPK(23L)).thenReturn(EntityMocks.createTestUser());

        List<Order> orders = orderService.findAll();
        Assert.assertEquals(singleOrder, orders);

        Mockito.verify(orderDao).findAll();

        Mockito.doThrow(new DatabaseException()).when(orderDao).findAll();

        Assert.assertNull(orderService.findAll());
    }

    @Test
    public void findAllAwaitingAnswer() throws DatabaseException {
        OrderService orderService = new OrderServiceImpl(orderDao, userDao);

        Order testOrder = EntityMocks.createTestOrder();
        List<Order> singleOrder = Collections.singletonList(testOrder);
        Mockito.when(orderDao.findAllAwaitingAnswer("id", true, 1, 0)).thenReturn(singleOrder);
        Mockito.when(userDao.findByPK(23L)).thenReturn(EntityMocks.createTestUser());

        List<Order> orders = orderService.findAllAwaitingAnswer("id", true, 1, 0);
        Assert.assertEquals(singleOrder, orders);

        Mockito.verify(orderDao).findAllAwaitingAnswer("id", true, 1, 0);

        Mockito.doThrow(new DatabaseException()).when(orderDao).findAllAwaitingAnswer("id", true, 1, 0);

        Assert.assertNull(orderService.findAllAwaitingAnswer("id", true, 1, 0));
    }

    @Test
    public void findAllAwaitingMaster() throws DatabaseException {
        OrderService orderService = new OrderServiceImpl(orderDao, userDao);

        Order testOrder = EntityMocks.createTestOrder();
        List<Order> singleOrder = Collections.singletonList(testOrder);
        Mockito.when(orderDao.findAllAwaitingMaster("id", true, 1, 0)).thenReturn(singleOrder);
        Mockito.when(userDao.findByPK(23L)).thenReturn(EntityMocks.createTestUser());

        List<Order> orders = orderService.findAllAwaitingMaster("id", true, 1, 0);
        Assert.assertEquals(singleOrder, orders);

        Mockito.verify(orderDao).findAllAwaitingMaster("id", true, 1, 0);

        Mockito.doThrow(new DatabaseException()).when(orderDao).findAllAwaitingMaster("id", true, 1, 0);

        Assert.assertNull(orderService.findAllAwaitingMaster("id", true, 1, 0));
    }

    @Test
    public void findAllByAuthorId() throws DatabaseException {
        OrderService orderService = new OrderServiceImpl(orderDao, userDao);

        Order testOrder = EntityMocks.createTestOrder();
        List<Order> singleOrder = Collections.singletonList(testOrder);
        Mockito.when(orderDao.findAllByAuthor(2L, "id", true, 1, 0)).thenReturn(singleOrder);
        Mockito.when(userDao.findByPK(23L)).thenReturn(EntityMocks.createTestUser());

        List<Order> orders = orderService.findAllByAuthorId(2L, "id", true, 1, 0);
        Assert.assertEquals(singleOrder, orders);

        Mockito.verify(orderDao).findAllByAuthor(2L, "id", true, 1, 0);

        Mockito.doThrow(new DatabaseException()).when(orderDao).findAllByAuthor(2L, "id", true, 1, 0);

        Assert.assertNull(orderService.findAllByAuthorId(2L, "id", true, 1, 0));
    }

    @Test
    public void findAllByMasterId() throws DatabaseException {
        OrderService orderService = new OrderServiceImpl(orderDao, userDao);

        Order testOrder = EntityMocks.createTestOrder();
        List<Order> singleOrder = Collections.singletonList(testOrder);
        Mockito.when(orderDao.findAllByMaster(2L, "id", true, 1, 0)).thenReturn(singleOrder);
        Mockito.when(userDao.findByPK(23L)).thenReturn(EntityMocks.createTestUser());

        List<Order> orders = orderService.findAllByMasterId(2L, "id", true, 1, 0);
        Assert.assertEquals(singleOrder, orders);

        Mockito.verify(orderDao).findAllByMaster(2L, "id", true, 1, 0);

        Mockito.doThrow(new DatabaseException()).when(orderDao).findAllByMaster(2L, "id", true, 1, 0);

        Assert.assertNull(orderService.findAllByMasterId(2L, "id", true, 1, 0));
    }

    @Test
    public void findAllByManagerId() throws DatabaseException {
        OrderService orderService = new OrderServiceImpl(orderDao, userDao);

        Order testOrder = EntityMocks.createTestOrder();
        List<Order> singleOrder = Collections.singletonList(testOrder);
        Mockito.when(orderDao.findAllByManager(2L, "id", true, 1, 0)).thenReturn(singleOrder);
        Mockito.when(userDao.findByPK(23L)).thenReturn(EntityMocks.createTestUser());

        List<Order> orders = orderService.findAllByManagerId(2L, "id", true, 1, 0);
        Assert.assertEquals(singleOrder, orders);

        Mockito.verify(orderDao).findAllByManager(2L, "id", true, 1, 0);

        Mockito.doThrow(new DatabaseException()).when(orderDao).findAllByManager(2L, "id", true, 1, 0);

        Assert.assertNull(orderService.findAllByManagerId(2L, "id", true, 1, 0));
    }

    @Test
    public void answerOrder() throws DatabaseException {
        OrderService orderService = new OrderServiceImpl(orderDao, userDao);

        Order testOrder = EntityMocks.createTestOrder();
        Mockito.when(userDao.findByPK(23L)).thenReturn(EntityMocks.createTestUser());

        Mockito.when(orderDao.findByPK(testOrder.getId())).thenReturn(testOrder);

        Assert.assertTrue(orderService.answerOrder(testOrder));

        Mockito.verify(orderDao).update(Mockito.any(Order.class));

        Mockito.verify(orderDao).findByPK(testOrder.getId());

        Mockito.verify(userDao, Mockito.times(3)).findByPK(23L);

        Mockito.verify(userDao).update(Mockito.any(User.class));

        Mockito.doThrow(new DatabaseException()).when(orderDao).findByPK(testOrder.getId());

        Assert.assertFalse(orderService.answerOrder(testOrder));
    }

    @Test
    public void linkMaster() throws DatabaseException {
        OrderService orderService = new OrderServiceImpl(orderDao, userDao);

        Order testOrder = EntityMocks.createTestOrder();
        Mockito.when(userDao.findByPK(23L)).thenReturn(EntityMocks.createTestUser());

        Mockito.when(orderDao.findByPK(testOrder.getId())).thenReturn(testOrder);

        Assert.assertTrue(orderService.linkMaster(testOrder));

        Mockito.verify(orderDao).update(Mockito.any(Order.class));

        Mockito.verify(orderDao).findByPK(testOrder.getId());

        Mockito.verify(userDao, Mockito.times(3)).findByPK(23L);

        Mockito.verify(userDao).update(Mockito.any(User.class));

        Mockito.doThrow(new DatabaseException()).when(orderDao).findByPK(testOrder.getId());

        Assert.assertFalse(orderService.linkMaster(testOrder));
    }

    @Test
    public void saveOrder() throws DatabaseException {
        OrderService orderService = new OrderServiceImpl(orderDao, userDao);

        Order testOrder = EntityMocks.createTestOrder();

        Assert.assertTrue(orderService.saveOrder(testOrder));

        Mockito.verify(orderDao).persist(Mockito.any(Order.class));

        Mockito.verify(userDao).update(Mockito.any(User.class));

        Mockito.doThrow(new DatabaseException()).when(orderDao).persist(Mockito.any(Order.class));

        Assert.assertFalse(orderService.saveOrder(testOrder));
    }

    @Test
    public void finishOrder() throws DatabaseException {
        OrderService orderService = new OrderServiceImpl(orderDao, userDao);

        Order testOrder = EntityMocks.createTestOrder();
        Mockito.when(userDao.findByPK(23L)).thenReturn(EntityMocks.createTestUser());

        Mockito.when(orderDao.findByPK(testOrder.getId())).thenReturn(testOrder);

        Assert.assertTrue(orderService.finishOrder(testOrder));

        Mockito.verify(orderDao).update(Mockito.any(Order.class));

        Mockito.verify(orderDao).findByPK(testOrder.getId());

        Mockito.verify(userDao, Mockito.times(3)).findByPK(23L);

        Mockito.verify(userDao, Mockito.times(3)).update(Mockito.any(User.class));

        Mockito.doThrow(new DatabaseException()).when(orderDao).findByPK(testOrder.getId());

        Assert.assertFalse(orderService.finishOrder(testOrder));
    }
}