package com.gelo.controller.router.handlers;

import com.gelo.controller.router.annotation.PostMapping;
import com.gelo.controller.router.annotation.PreAuthorize;
import com.gelo.model.domain.Order;
import com.gelo.model.domain.RoleType;
import com.gelo.model.domain.User;
import com.gelo.services.OrderService;
import com.gelo.services.UserService;
import com.gelo.util.BeanStorage;
import com.gelo.util.Transport;
import com.gelo.util.constants.Paths;
import com.gelo.validation.Alert;
import com.gelo.validation.formats.LongFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.gelo.validation.Alert.single;

/**
 * Handles master enroll on order action.
 * Action is prevented if user has more than 5 active orders.
 * If everything is ok master starts working on the order.
 */
@PostMapping
@PreAuthorize(role = RoleType.ROLE_MASTER)
public class MasterEnrollHandler implements Handler {
    OrderService orderService = BeanStorage.INSTANCE.get(OrderService.class);
    UserService userService = BeanStorage.INSTANCE.get(UserService.class);

    @Override
    public Transport execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


        User loggedUser = (User) request.getSession(true).getAttribute("user");
        if (loggedUser.getActiveOrdersCount() > 5) {
            request.setAttribute("alerts", single(Alert.danger("master.too.much.orders")));
            return Transport.absolute(Paths.HOME_PAGE);
        }
        LongFormat format = new LongFormat(request.getParameter("order_id"));

        if (!format.valid()) {
            request.setAttribute("alerts", single(Alert.danger("number.format.incorrect")));
            return Transport.absolute(Paths.MASTER_PAGE);
        }

        Order order = new Order();
        order.setId(format.parseLong());
        order.setMaster(loggedUser);
        boolean success = orderService.linkMaster(order);
        if (success) {
            request.setAttribute("alerts", single(Alert.success("master.cheer.up")));
        } else {
            request.setAttribute("alerts", single(Alert.success("went.wrong")));
        }

        request.getSession().setAttribute("user", userService.findByEmail(loggedUser.getEmail()));

        return Transport.absolute(Paths.MASTER_PAGE);
    }
}
