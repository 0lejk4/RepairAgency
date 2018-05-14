package com.gelo.factory;

import com.gelo.model.dao.*;
import com.gelo.model.dao.impl.OrderDaoImpl;
import com.gelo.model.dao.impl.PermissionDaoImpl;
import com.gelo.model.dao.impl.ReviewDaoImpl;
import com.gelo.model.dao.impl.UserDaoImpl;
import com.gelo.model.dao.ReviewDao;
import com.gelo.model.dao.UserDao;
import com.gelo.model.dao.impl.ReviewDaoImpl;
import com.gelo.model.dao.impl.UserDaoImpl;

/**
 * Layer of abstraction to make Dependency Injection easier.
 * Is mainly used in default constructors of service layers.
 */
public class DaoFactory {

    /**
     * Gets user dao instance.
     *
     * @return the user dao instance
     */
    public static UserDao getUserDaoInstance() {
        return new UserDaoImpl();
    }

    /**
     * Gets role dao instance.
     *
     * @return the role dao instance
     */
    public static PermissionDao getPermissionDaoInstance() {
        return new PermissionDaoImpl();
    }

    /**
     * Gets order dao instance.
     *
     * @return the order dao instance
     */
    public static OrderDao getOrderDaoInstance() {
        return new OrderDaoImpl();
    }

    /**
     * Gets review dao instance.
     *
     * @return the review dao instance
     */
    public static ReviewDao getReviewDaoInstance() {
        return new ReviewDaoImpl();
    }


}
