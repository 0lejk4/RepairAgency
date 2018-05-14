package com.gelo.controller.router.handlers;

import com.gelo.model.domain.User;
import com.gelo.services.OrderService;
import com.gelo.services.impl.OrderServiceImpl;
import com.gelo.util.Transport;
import com.gelo.util.constants.Paths;
import com.gelo.model.domain.User;
import com.gelo.services.OrderService;
import com.gelo.services.impl.OrderServiceImpl;
import com.gelo.util.Transport;
import com.gelo.util.constants.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Shows client`s history page with all
 * orders user has at the moment and had in past
 */
public class ClientHistoryPageHandler implements Handler {
    @Override
    public Transport execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        OrderService orderService = new OrderServiceImpl();
        User loggedUser = (User) request.getSession(true).getAttribute("user");
        request.setAttribute("orders", orderService.findAllByAuthorId(loggedUser.getId()));
        return Transport.absForward(Paths.HISTORY_PAGE_JSP);
    }
}
