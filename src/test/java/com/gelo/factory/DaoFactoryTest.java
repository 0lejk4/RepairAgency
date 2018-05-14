package com.gelo.factory;

import com.gelo.model.dao.UserDao;
import com.gelo.model.dao.UserDao;
import org.junit.Assert;
import org.junit.Test;

public class DaoFactoryTest {
    @Test
    public void isSameUserDaoObject(){
        UserDao dao1 = DaoFactory.getUserDaoInstance();
        UserDao dao2 = DaoFactory.getUserDaoInstance();

        Assert.assertNotSame(dao1 , dao2);
    }
}
