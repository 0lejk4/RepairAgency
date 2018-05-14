package com.gelo.controller.router.handlers;

import com.gelo.controller.router.security.PreAuthorize;
import com.gelo.factory.ServiceFactory;
import com.gelo.model.domain.Order;
import com.gelo.model.domain.RoleType;
import com.gelo.model.domain.User;
import com.gelo.services.OrderService;
import com.gelo.util.Transport;
import com.gelo.util.constants.Paths;
import com.gelo.validation.Alert;
import com.gelo.validation.forms.OrderForm;
import com.gelo.model.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;

import static com.gelo.validation.Alert.single;

/**
 * Handles order creation action.
 * Form data is validated by OrderForm class.
 * Prevents creation if user has more than 5 active orders.
 * Forwards to the order creation page.
 */
@PreAuthorize(role = RoleType.ROLE_USER)
public class CreateOrderHandler implements Handler {
    @Override
    public Transport execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = (User) request.getSession().getAttribute("user");
        if(user.getActiveOrdersCount() > 5){
            request.setAttribute("alerts", single(Alert.danger("user.too.much.orders")));
            return Transport.absForward(Paths.HOME_PAGE);
        }
        OrderForm form = new OrderForm(
                request.getParameter("authorDescription")
        );
        if (form.valid()) {
            OrderService orderService = ServiceFactory.getOrderServiceInstance();
            Order order = form.parseOrder();
            order.setAuthor(user);
            boolean success = orderService.saveOrder(order);
            if(success){
                request.setAttribute("alerts", single(Alert.success("order.create.success")));
            }else {
                request.setAttribute("alerts", single(Alert.success("went.wrong")));
            }

        } else {
            request.setAttribute("alerts", form.getErrorList());
        }

        return Transport.absForward(Paths.ORDER_CREATE_PAGE);
    }
}
