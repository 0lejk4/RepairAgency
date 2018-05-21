package com.gelo.controller.router.handlers;

import com.gelo.controller.router.annotation.GetMapping;
import com.gelo.factory.ServiceFactory;
import com.gelo.model.domain.Review;
import com.gelo.model.domain.User;
import com.gelo.services.ReviewService;
import com.gelo.services.UserService;
import com.gelo.services.impl.PaginatorImpl;
import com.gelo.util.Transport;
import com.gelo.validation.pagination.ReviewPaginationForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Shows some users profile with all reviews it has.
 */
@GetMapping
public class ProfileHandler implements Handler {
    @Override
    public Transport execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String profile_id_param = request.getParameter("profile_id");
        UserService userService = ServiceFactory.getUserServiceInstance();
        ReviewService reviewService = ServiceFactory.getReviewServiceInstance();
        PaginatorImpl<Review> orderPaginationService = new PaginatorImpl<>();
        User loggedUser = (User) request.getSession(false).getAttribute("user");
        boolean isOwn = (profile_id_param == null);

        long profileId;
        if (isOwn) {
            profileId = loggedUser.getId();
        } else {
            profileId = Long.parseLong(profile_id_param);
        }
        boolean can_review = false;

        if (!isOwn) {
            can_review = reviewService.canAuthorReviewMaster(loggedUser.getId(), profileId);
        }

        request.setAttribute("can_review", can_review);

        ReviewPaginationForm form = new ReviewPaginationForm(
                request.getParameter("page"),
                request.getParameter("count"),
                request.getParameter("ascending"),
                request.getParameter("order_field")
        );

        User profile;
        if (isOwn) {
            profile = (User) request.getSession(false).getAttribute("user");
        } else {
            profile = userService.findById(Long.parseLong(profile_id_param));
        }

        request.setAttribute("profile_user", profile);

        long allCount = reviewService.countAllByMasterId(profileId);

        orderPaginationService.paginate(
                form, () -> reviewService.getAllByMasterIdPaginated(
                        profileId,
                        form.getOrderField(),
                        form.getAscending(),
                        form.getCount(),
                        (form.getPage() - 1) * form.getCount()),
                allCount);

        request.setAttribute("all_count", allCount);
        request.setAttribute("page_count", orderPaginationService.getPageCount());
        request.setAttribute("order_field", orderPaginationService.getOrderField());
        request.setAttribute("ascending", orderPaginationService.getAscending());
        request.setAttribute("count", orderPaginationService.getCount());
        request.setAttribute("page", orderPaginationService.getPage());
        request.setAttribute("reviews", orderPaginationService.getEntities());

        if (orderPaginationService.isValid()) {
            request.setAttribute("alerts", form.getErrorList());
        }

        return Transport.absolute("/profile.jsp");
    }
}
