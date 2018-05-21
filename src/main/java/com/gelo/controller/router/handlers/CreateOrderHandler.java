package com.gelo.controller.router.handlers;

import com.gelo.controller.router.annotation.PostMapping;
import com.gelo.controller.router.annotation.PreAuthorize;
import com.gelo.factory.ServiceFactory;
import com.gelo.model.domain.Order;
import com.gelo.model.domain.RoleType;
import com.gelo.model.domain.User;
import com.gelo.services.OrderService;
import com.gelo.services.UserService;
import com.gelo.util.Transport;
import com.gelo.util.constants.Paths;
import com.gelo.validation.Alert;
import com.gelo.validation.forms.OrderForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.gelo.validation.Alert.single;

/**
 * Handles order creation action.
 * Form data is validated by OrderForm class.
 * Prevents creation if user has more than 5 active orders.
 * Forwards to the order creation page.
 */
@PostMapping
@PreAuthorize(role = RoleType.ROLE_USER)
public class CreateOrderHandler implements Handler {
    @Override
    public Transport execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User loggedUser = (User) request.getSession().getAttribute("user");
        if (loggedUser.getActiveOrdersCount() > 5) {
            request.setAttribute("alerts", single(Alert.danger("user.too.much.orders")));
            return Transport.absolute(Paths.HOME_PAGE);
        }
        OrderForm form = new OrderForm(
                request.getParameter("authorDescription")
        );
        if (form.valid()) {
            OrderService orderService = ServiceFactory.getOrderServiceInstance();
            Order order = form.parseOrder();
            order.setAuthor(loggedUser);
            boolean success = orderService.saveOrder(order);
            if (success) {
                request.setAttribute("alerts", single(Alert.success("order.create.success")));
            } else {
                request.setAttribute("alerts", single(Alert.success("went.wrong")));
            }
        } else {
            request.setAttribute("alerts", form.getErrorList());
        }

        UserService userService = ServiceFactory.getUserServiceInstance();
        request.getSession().setAttribute("user", userService.findByEmail(loggedUser.getEmail()));

        return Transport.absolute(Paths.ORDER_CREATE_PAGE);
    }
}
