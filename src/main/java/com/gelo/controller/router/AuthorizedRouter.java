package com.gelo.controller.router;

import com.gelo.controller.router.annotation.GetMapping;
import com.gelo.controller.router.annotation.PostMapping;
import com.gelo.controller.router.annotation.PreAuthorize;
import com.gelo.controller.router.handlers.Handler;
import com.gelo.model.domain.PermissionType;
import com.gelo.model.domain.RoleType;
import com.gelo.model.domain.User;
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

import static com.gelo.controller.router.annotation.GetMapping.GET_STR;
import static com.gelo.controller.router.annotation.PostMapping.POST_STR;

/**
 * Thread-safe singleton router that is instantiated with first servlet call.
 * This implementation of router looks for PreAuthorize annotation and does all needed authorization.
 * On instantiation router reads all handlers from a file given in method call.
 */
public class AuthorizedRouter implements Router {

    private static final Logger logger = Logger.getLogger(AuthorizedRouter.class);

    /**
     * Map with all handlers for specific routes
     */
    private final Map<String, Handler> routes = new HashMap<>();

    /**
     * Transport that is used for '/' path
     */
    private final Transport default_transport = Transport.redirect(Paths.HOME_PAGE);

    /**
     * Transport that is used to show 404 error page
     */
    private final Transport not_found_transport = Transport.absolute(Paths.NOT_FOUND);


    /**
     * Constructor that reads all handlers from file.
     *
     * @param routesFile file with handler mapping
     */
    public AuthorizedRouter(String routesFile) {
        Properties properties = new Properties();
        try {
            properties.load(AuthorizedRouter.class.getClassLoader()
                    .getResourceAsStream(routesFile));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        //Loop through paths
        for (Object key : properties.keySet()) {
            String path = (String) key;
            try {
                //Try getting handler with given full name
                Class<?> described = Class.forName(properties.getProperty(path));

                //Implements Handler ?
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
     * Method that checks if the request method matches the one placed on the Handler Class,
     * if there is no marker annotations method can be used for any request method.
     * In amount of such annotations can grow to be able to handle different request methods.
     *
     * @param handler handler which is going to be authorized
     * @param request request to look-up for user in session
     * @return true if method matches and handler can be used with that request method
     */
    private boolean checkMethod(Class<? extends Handler> handler, HttpServletRequest request) {
        GetMapping get = handler.getAnnotation(GetMapping.class);
        PostMapping post = handler.getAnnotation(PostMapping.class);

        //Skip this step if the request is from Dispatcher forward
        if(request.getAttribute("from_dispatcher") != null) return true;

        if (get == null && post == null) {
            return true;
        }

        String requestMethod = request.getMethod();

        if (get != null && GET_STR.equals(requestMethod)) {
            return true;
        }

        if (post != null && POST_STR.equals(requestMethod)) {
            return true;
        }

        return false;
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

        //No user in session scope so roles or permissions cant be matched
        if (user == null) {
            return false;
        }

        if (preAuthorize.role().equals(RoleType.NONE)) {
            boolean hasAllPermissions = true;

            //Check if user has Permission
            for (PermissionType pT : preAuthorize.permissions()) {
                if (!SecurityUtils.hasPermission(user, pT)) {
                    hasAllPermissions = false;
                    break;
                }
            }

            return hasAllPermissions;
        } else {
            return SecurityUtils.hasRole(user, preAuthorize.role());
        }

    }

    /**
     * Specific authorized implementation of router`s handle method
     *
     * @return Transport action that should happen next
     */
    public Transport handle(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = request.getRequestURI();
        if (path.equals("/")) return default_transport;

        Handler handler = routes.get(path);

        //No handler - not found
        if (handler == null) return not_found_transport;

        //Check if user can access
        boolean authorized = preAuthorize(handler.getClass(), request);

        boolean requestMethodMatches = checkMethod(handler.getClass(), request);

        request.setAttribute("from_dispatcher", new Object());

        //Not authorized - not found, user should not know what page exists but he cant access
        return (authorized && requestMethodMatches)
                ? handler.execute(request, response)
                : not_found_transport;
    }
}
