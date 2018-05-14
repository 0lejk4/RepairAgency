package com.gelo.services.impl;

import com.gelo.mock.entity.EntityMocks;
import com.gelo.model.dao.PermissionDao;
import com.gelo.model.dao.UserDao;
import com.gelo.model.domain.User;
import com.gelo.model.exception.DatabaseException;
import com.gelo.services.UserService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Collections;

public class UserServiceImplTest {
    @Mock
    private UserDao userDao;
    @Mock
    private PermissionDao permissionDao;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void findAll() throws DatabaseException {
        UserService userService = new UserServiceImpl(userDao, permissionDao);
        User user = EntityMocks.createTestUser();

        Mockito.when(userDao.findAll()).thenReturn(Collections.singletonList(user));

        Assert.assertEquals(userService.findAll(), Collections.singletonList(user));

        Mockito.verify(userDao).findAll();

        Mockito.doThrow(new DatabaseException()).when(userDao).findAll();

        Assert.assertNull(userService.findAll());
    }

    @Test
    public void findFiveBestMasters() throws DatabaseException {
        UserService userService = new UserServiceImpl(userDao, permissionDao);
        User user = EntityMocks.createTestUser();

        Mockito.when(userDao.findFiveBestMasters()).thenReturn(Collections.singletonList(user));

        Assert.assertEquals(userService.findFiveBestMasters(), Collections.singletonList(user));

        Mockito.verify(userDao).findFiveBestMasters();

        Mockito.doThrow(new DatabaseException()).when(userDao).findFiveBestMasters();

        Assert.assertNull(userService.findFiveBestMasters());
    }

    @Test
    public void save() throws DatabaseException {
        UserService userService = new UserServiceImpl(userDao, permissionDao);
        User user = EntityMocks.createTestUser();

        Assert.assertTrue(userService.save(user));

        Mockito.verify(userDao).persist(user);

        Mockito.doThrow(new DatabaseException()).when(userDao).persist(user);

        Assert.assertFalse(userService.save(user));
    }

    @Test
    public void findByEmail() throws DatabaseException{
        UserService userService = new UserServiceImpl(userDao, permissionDao);
        Mockito.when(permissionDao.getPermissionsByRoleId(0L))
                .thenReturn(EntityMocks.createUserPermissions());
        Mockito.when(userDao.findByEmail("test@test.com")).thenReturn(EntityMocks.createTestUser());

        User actual = userService.findByEmail("test@test.com");

        Assert.assertEquals(23L, actual.getId().longValue());
        Assert.assertEquals("Ukraine", actual.getCountry());
        Assert.assertEquals("asda213dasd", actual.getPassword());
        Assert.assertEquals("test@test.com", actual.getEmail());
        Assert.assertEquals("Oleg", actual.getName());

        Mockito.verify(userDao).findByEmail("test@test.com");
    }

    @Test
    public void emailTaken() throws DatabaseException {
        UserService userService = new UserServiceImpl(userDao, permissionDao);

        String mockEmail = "test@test.ua";
        Mockito.when(userDao.emailTaken(mockEmail)).thenReturn(false);

        Assert.assertFalse(userService.emailTaken(mockEmail));

        Mockito.verify(userDao).emailTaken(mockEmail);

        Mockito.doThrow(new DatabaseException()).when(userDao).emailTaken(mockEmail);

        Assert.assertTrue(userService.emailTaken(mockEmail));
    }

    @Test
    public void findById() throws DatabaseException {
        UserService userService = new UserServiceImpl(userDao, permissionDao);
        User user = EntityMocks.createTestUser();

        Mockito.when(userDao.findByPK(23L)).thenReturn(user);

        Assert.assertEquals(userService.findById(23L), user);

        Mockito.verify(userDao).findByPK(23L);

        Mockito.doThrow(new DatabaseException()).when(userDao).findByPK(23L);

        Assert.assertNull(userService.findById(23L));
    }



}