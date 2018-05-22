package com.gelo.factory;

import com.gelo.model.dao.OrderDao;
import com.gelo.model.dao.PermissionDao;
import com.gelo.model.dao.ReviewDao;
import com.gelo.model.dao.UserDao;
import com.gelo.model.dao.impl.OrderDaoImpl;
import com.gelo.model.dao.impl.PermissionDaoImpl;
import com.gelo.model.dao.impl.ReviewDaoImpl;
import com.gelo.model.dao.impl.UserDaoImpl;
import com.gelo.persistance.ConnectionManager;

/**
 * Layer of abstraction to make Dependency Injection easier.
 * Is mainly used in default constructors of service layers.
 */
public class DaoFactory {

    private ConnectionManager connectionManager;

    public DaoFactory(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * Gets user dao instance.
     *
     * @return the user dao instance
     */
    public UserDao getUserDaoInstance() {
        return new UserDaoImpl(connectionManager);
    }

    /**
     * Gets role dao instance.
     *
     * @return the role dao instance
     */
    public PermissionDao getPermissionDaoInstance() {
        return new PermissionDaoImpl(connectionManager);
    }

    /**
     * Gets order dao instance.
     *
     * @return the order dao instance
     */
    public OrderDao getOrderDaoInstance() {
        return new OrderDaoImpl(connectionManager);
    }

    /**
     * Gets review dao instance.
     *
     * @return the review dao instance
     */
    public ReviewDao getReviewDaoInstance() {
        return new ReviewDaoImpl(connectionManager);
    }


}
