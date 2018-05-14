package com.gelo.controller.router.handlers;


import com.gelo.factory.ServiceFactory;
import com.gelo.model.domain.RoleType;
import com.gelo.controller.router.security.PreAuthorize;
import com.gelo.services.OrderService;
import com.gelo.services.UserService;
import com.gelo.util.Transport;
import com.gelo.util.constants.Paths;
import com.gelo.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Shows admin page with all orders and users that exists.
 */
@PreAuthorize(role = RoleType.ROLE_ADMIN)
public class AdminPageHandler implements Handler {

    @Override
    public Transport execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        OrderService orderService = ServiceFactory.getOrderServiceInstance();
        UserService userService = ServiceFactory.getUserServiceInstance();
        request.setAttribute("orders", orderService.findAll());
        request.setAttribute("users", userService.findAll());
        return Transport.absForward(Paths.ADMIN_PAGE_JSP);
    }

}
