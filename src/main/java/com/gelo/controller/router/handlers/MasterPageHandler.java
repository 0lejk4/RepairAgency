package com.gelo.controller.router.handlers;

import com.gelo.controller.router.security.PreAuthorize;
import com.gelo.factory.ServiceFactory;
import com.gelo.model.domain.RoleType;
import com.gelo.model.domain.User;
import com.gelo.services.OrderService;
import com.gelo.util.Transport;
import com.gelo.util.constants.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Forwards to master page and populates it with orders that master can enroll on
 * and  orders that master is working in or done in past.
 */
@PreAuthorize(role = RoleType.ROLE_MASTER)
public class MasterPageHandler implements Handler {
    @Override
    public Transport execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        OrderService orderService = ServiceFactory.getOrderServiceInstance();
        //Todo: add pagination
        request.setAttribute("orders", orderService.findAllAwaitingMaster());
        User loggedUser = (User) request.getSession(true).getAttribute("user");
        request.setAttribute("workHistory", orderService.findAllByMasterId(loggedUser.getId()));
        return Transport.absForward(Paths.MASTER_PAGE_JSP);
    }
}
