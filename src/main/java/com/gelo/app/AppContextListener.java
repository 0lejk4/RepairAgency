package com.gelo.app;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
/**
 * The type App context listener.
 * Is used to create WebApplication instance set it in
 * servletContext( this is not used currently) but can be used in future
 */
@WebListener
public class AppContextListener implements ServletContextListener {
    private static final Logger LOGGER = LogManager.getLogger(AppContextListener.class);
    private WebApplication webApplication = new WebApplication();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("webApplication", webApplication);
        webApplication.setServletContext(sce.getServletContext());
        webApplication.init();

        LOGGER.info("WebApplication initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("WebApplication destroyed");
    }
}