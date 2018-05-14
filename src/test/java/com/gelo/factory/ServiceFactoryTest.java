package com.gelo.factory;

import com.gelo.services.UserService;
import com.gelo.services.UserService;
import org.junit.Assert;
import org.junit.Test;

public class ServiceFactoryTest {
    @Test
    public void isSameUserDaoObject(){
        UserService service1 = ServiceFactory.getUserServiceInstance();
        UserService service2 = ServiceFactory.getUserServiceInstance();

        Assert.assertNotSame(service1 ,service2);
    }
}
