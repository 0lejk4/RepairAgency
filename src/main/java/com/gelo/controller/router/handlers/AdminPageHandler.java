package com.gelo.controller.router.handlers;


import com.gelo.controller.router.annotation.GetMapping;
import com.gelo.controller.router.annotation.PreAuthorize;
import com.gelo.model.domain.RoleType;
import com.gelo.services.OrderService;
import com.gelo.services.UserService;
import com.gelo.util.BeanStorage;
import com.gelo.util.Transport;
import com.gelo.util.constants.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Shows admin page with all orders and users that exists.
 */
@GetMapping
@PreAuthorize(role = RoleType.ROLE_ADMIN)
public class AdminPageHandler implements Handler {
    UserService userService = BeanStorage.INSTANCE.get(UserService.class);
    OrderService orderService = BeanStorage.INSTANCE.get(OrderService.class);
    @Override
    public Transport execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("orders", orderService.findAll());
        request.setAttribute("users", userService.findAll());
        return Transport.absolute(Paths.ADMIN_PAGE_JSP);
    }

}
