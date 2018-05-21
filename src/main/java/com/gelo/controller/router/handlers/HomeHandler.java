package com.gelo.controller.router.handlers;


import com.gelo.controller.router.annotation.GetMapping;
import com.gelo.factory.ServiceFactory;
import com.gelo.services.UserService;
import com.gelo.util.Transport;
import com.gelo.util.constants.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Forwards to home page and populates top 5 masters collection
 */
@GetMapping
public class HomeHandler implements Handler {

    @Override
    public Transport execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserService userService = ServiceFactory.getUserServiceInstance();
        request.setAttribute("best_masters", userService.findFiveBestMasters());
        return Transport.absolute(Paths.HOME_PAGE_JSP);
    }
}
