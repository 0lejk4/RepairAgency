package com.gelo.factory;

import com.gelo.services.OrderService;
import com.gelo.services.ReviewService;
import com.gelo.services.UserService;
import com.gelo.services.impl.OrderServiceImpl;
import com.gelo.services.impl.ReviewServiceImpl;
import com.gelo.services.impl.UserServiceImpl;

/**
 * Layer of abstraction to make Dependency Injection easier.
 * Is mainly used in handlers to get service instance.
 */
public class ServiceFactory {
    //Todo: Make this object pool or leave as it is if branch JdbcTemplate will be successful
    /**
     * Gets user service instance.
     *
     * @return the user service instance
     */
    public static UserService getUserServiceInstance() {
        return new UserServiceImpl();
    }

    /**
     * Gets order service instance.
     *
     * @return the order service instance
     */
    public static OrderService getOrderServiceInstance() {
        return new OrderServiceImpl();
    }

    /**
     * Gets review service instance.
     *
     * @return the review service instance
     */
    public static ReviewService getReviewServiceInstance() {
        return new ReviewServiceImpl();
    }

}
