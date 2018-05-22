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
import com.gelo.validation.forms.AnswerOrderForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.gelo.validation.Alert.single;

/**
 * Handles Manager answer order action.
 * Validation is done using AnswerOrderForm class.
 * Action is prevented if user has more than 5 active orders.
 * If everything is ok order is answered by master,
 * if not - all errors are saved in request.
 */
@PostMapping
@PreAuthorize(role = RoleType.ROLE_MANAGER)
public class ManagerAnswerHandler implements Handler {
    UserService userService = BeanStorage.INSTANCE.get(UserService.class);
    OrderService orderService = BeanStorage.INSTANCE.get(OrderService.class);

    @Override
    public Transport execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User loggedUser = (User) request.getSession(true).getAttribute("user");
        if (loggedUser.getActiveOrdersCount() > 5) {
            request.setAttribute("alerts", single(Alert.danger("manager.too.much.orders")));
            return Transport.absolute(Paths.HOME_PAGE);
        }


        AnswerOrderForm form = new AnswerOrderForm(
                request.getParameter("order_id"),
                request.getParameter("managerDescription"),
                request.getParameter("price")
        );


        if (!form.valid()) {
            request.setAttribute("alerts", form.getErrorList());
            return Transport.absolute(Paths.MANAGER_PAGE);
        }

        Order order = form.parseOrder();
        if (request.getParameter("accepted") != null) {
            order.setAccepted(true);
        } else if (request.getParameter("declined") != null) {
            order.setAccepted(false);
        } else {
            request.setAttribute("alerts", single(Alert.success("Nor accepted, nor declined")));
            return Transport.absolute(Paths.MANAGER_PAGE);
        }
        order.setManager(loggedUser);
        boolean success = orderService.answerOrder(order);
        if (success) {
            request.setAttribute("alerts", single(Alert.success("manager.cheer.up")));
        } else {
            request.setAttribute("alerts", single(Alert.success("went.wrong")));
        }

        request.getSession().setAttribute("user", userService.findByEmail(loggedUser.getEmail()));

        return Transport.absolute(Paths.MANAGER_PAGE);
    }
}
