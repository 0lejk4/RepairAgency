package com.gelo.controller.router.handlers;

import com.gelo.controller.router.annotation.GetMapping;
import com.gelo.model.domain.Order;
import com.gelo.model.domain.User;
import com.gelo.services.OrderService;
import com.gelo.services.impl.OrderServiceImpl;
import com.gelo.services.impl.PaginatorImpl;
import com.gelo.util.Transport;
import com.gelo.util.constants.Paths;
import com.gelo.validation.pagination.OrderPaginationForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Shows client`s history page with all
 * orders user has at the moment and had in past
 */
@GetMapping
public class ClientHistoryPageHandler implements Handler {
    @Override
    public Transport execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        OrderService orderService = new OrderServiceImpl();
        PaginatorImpl<Order> orderPaginationService = new PaginatorImpl<>();
        User loggedUser = (User) request.getSession(true).getAttribute("user");
        OrderPaginationForm form = new OrderPaginationForm(
                request.getParameter("page"),
                request.getParameter("count"),
                request.getParameter("ascending"),
                request.getParameter("order_field")
        );

        long allCount = orderService.countByAuthor(loggedUser.getId());

        orderPaginationService.paginate(
                form,
                () -> orderService.findAllByAuthorId(
                        loggedUser.getId(),
                        form.getOrderField(),
                        form.getAscending(),
                        form.getCount(),
                        (form.getPage() - 1) * form.getCount()
                ),
                allCount);

        request.setAttribute("all_count", allCount);
        request.setAttribute("page_count", orderPaginationService.getPageCount());
        request.setAttribute("order_field", orderPaginationService.getOrderField());
        request.setAttribute("ascending", orderPaginationService.getAscending());
        request.setAttribute("count", orderPaginationService.getCount());
        request.setAttribute("page", orderPaginationService.getPage());
        request.setAttribute("orders", orderPaginationService.getEntities());

        if (orderPaginationService.isValid()) {
            request.setAttribute("alerts", form.getErrorList());
        }

        return Transport.absolute(Paths.HISTORY_PAGE_JSP);
    }
}
