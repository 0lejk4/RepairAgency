package com.gelo.controller.router;

import com.gelo.controller.router.handlers.Handler;
import com.gelo.model.domain.PermissionType;
import com.gelo.model.domain.User;
import com.gelo.controller.router.security.PreAuthorize;
import com.gelo.util.SecurityUtils;
import com.gelo.util.Transport;
import com.gelo.util.constants.Paths;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Thread-safe singleton router that is instantiated with first servlet call.
 * This implementation of router looks for PreAuthorize annotation and does all needed authorization.
 * On instantiation router reads all handlers from a file given in method call.
 */
public class AuthorizedRouter implements Router {
    private static volatile AuthorizedRouter instance;

    /**
     * Return singleton instance of router
     *
     * @param routingFileName file with handler mapping
     * @return Router
     */
    public static AuthorizedRouter getInstance(String routingFileName) {
        AuthorizedRouter localInstance = instance;
        if (localInstance == null) {
            synchronized (AuthorizedRouter.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new AuthorizedRouter(routingFileName);
                }
            }
        }
        return localInstance;
    }

    private static final Logger logger = Logger.getLogger(AuthorizedRouter.class);

    /**
     * Map with all handlers for specific routes
     */
    private final Map<String, Handler> routes = new HashMap<>();


    /**
     * Constructor that reads all handlers from file.
     *
     * @param routesFile file with handler mapping
     */
    private AuthorizedRouter(String routesFile) {
        Properties properties = new Properties();

        try {
            properties.load(AuthorizedRouter.class.getClassLoader()
                    .getResourceAsStream(routesFile));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        for (Object key : properties.keySet()) {
            String path = (String) key;
            try {
                Class<?> described = Class.forName(properties.getProperty(path));

                if (Handler.class.isAssignableFrom(described)) {
                    Class<? extends Handler> handlerClazz = described.asSubclass(Handler.class);

                    Constructor<? extends Handler> actionConstructor = handlerClazz.getConstructor();
                    Handler action = actionConstructor.newInstance();
                    logger.info(String.format("Success adding handlers for path = %s", path));
                    routes.put(path, action);
                } else {
                    logger.warn(String.format("Class is not handlers, cant add it for path = %s", path));
                }
            } catch (ClassNotFoundException | NoSuchMethodException
                    | IllegalAccessException | InstantiationException
                    | InvocationTargetException e) {
                logger.warn(String.format("Error adding handlers for path = %s", path), e);
            }
        }
    }


    /**
     * Method that authorizes request by checking PreAuthorize annotation placed on handler
     *
     * @param handler handler which is going to be authorized
     * @param request request to look-up for user in session
     * @return true if user can access resource, false if not
     */
    private boolean preAuthorize(Class<? extends Handler> handler, HttpServletRequest request) {

        PreAuthorize preAuthorize = handler.getAnnotation(PreAuthorize.class);

        if (preAuthorize == null) {
            return true;
        }

        User user = (User) request.getSession(false).getAttribute("user");

        if (SecurityUtils.hasRole(user, preAuthorize.role())) {
            return true;
        }

        boolean hasAllPermissions = true;

        for (PermissionType pT : preAuthorize.permissions()) {
            if (!SecurityUtils.hasPermission(user, pT)) {
                hasAllPermissions = false;
                break;
            }
        }


        return hasAllPermissions;
    }

    /**
     * Specific authorized implementation of router`s handle method
     * @param path path requested
     * @return Transport action that should happen next
     */
    public Transport handle(String path, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Handler handler = routes.get(path);
        Transport notFound = Transport.absForward(Paths.NOT_FOUND);

        if (handler == null) return notFound;

        boolean authorized = preAuthorize(handler.getClass(), request);


        return authorized
                ? handler.execute(request, response)
                : notFound;
    }
}
