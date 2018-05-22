package com.gelo.app;

import com.gelo.controller.router.AuthorizedRouter;
import com.gelo.factory.DaoFactory;
import com.gelo.persistance.ConnectionManager;
import com.gelo.persistance.transaction.TransactionManager;
import com.gelo.services.OrderService;
import com.gelo.services.ReviewService;
import com.gelo.services.UserService;
import com.gelo.services.impl.OrderServiceImpl;
import com.gelo.services.impl.ReviewServiceImpl;
import com.gelo.services.impl.UserServiceImpl;
import com.gelo.util.BeanStorage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;

/**
 * The type Web application.
 * This class is the root of application
 * every startup preparation should happen this.
 */
public class WebApplication {
    private BeanStorage beanStorage;
    private DaoFactory daoFactory;
    private ServletContext servletContext;
    private static final Logger logger = LogManager.getLogger(WebApplication.class);

    /**
     * Init.
     */
    public void init() {
        beanStorage = BeanStorage.INSTANCE;
        preparePersistence();
        prepareServices();
        //Save router in servlet context
        servletContext.setAttribute("router", new AuthorizedRouter("routes.properties"));
    }

    /**
     * Prepares Persistence units
     */
    private void preparePersistence() {
        ConnectionManager connectionManager = new ConnectionManager();

        beanStorage.add(ConnectionManager.class, connectionManager);
        daoFactory = new DaoFactory(connectionManager);
        TransactionManager transactionManager = new TransactionManager(connectionManager);
        beanStorage.add(TransactionManager.class, transactionManager);
        logger.info("Persistence initialized");
    }

    /**
     * Prepares Services and puts them into BeanStorage
     */
    private void prepareServices() {
        UserService userService = new UserServiceImpl(daoFactory.getUserDaoInstance(), daoFactory.getPermissionDaoInstance());
        OrderService orderService = new OrderServiceImpl(daoFactory.getOrderDaoInstance(), daoFactory.getUserDaoInstance());
        ReviewService reviewService = new ReviewServiceImpl(daoFactory.getUserDaoInstance(), daoFactory.getReviewDaoInstance());

        beanStorage.add(UserService.class, userService);
        beanStorage.add(OrderService.class, orderService);
        beanStorage.add(ReviewService.class, reviewService);
        logger.info("Services initialized");
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }


}
