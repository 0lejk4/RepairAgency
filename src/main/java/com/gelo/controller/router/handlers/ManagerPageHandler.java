package com.gelo.controller.router.handlers;

import com.gelo.controller.router.security.PreAuthorize;
import com.gelo.model.domain.RoleType;
import com.gelo.model.domain.User;
import com.gelo.services.OrderService;
import com.gelo.services.impl.OrderServiceImpl;
import com.gelo.util.Transport;
import com.gelo.util.constants.Paths;
import com.gelo.model.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Manager page handler that populates request with awaiting answer orders
 * and with order management of Manager.
 */
@PreAuthorize(role = RoleType.ROLE_MANAGER)
public class ManagerPageHandler implements Handler {

    @Override
    public Transport execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        OrderService orderService = new OrderServiceImpl();
        //Todo: add pagination
        request.setAttribute("orders", orderService.findAllAwaitingAnswer());
        User loggedUser = (User) request.getSession(true).getAttribute("user");
        request.setAttribute("managementHistory", orderService.findAllByManagerId(loggedUser.getId()));
        return Transport.absForward(Paths.MANAGER_PAGE_JSP);
    }
}
