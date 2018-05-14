package com.gelo.controller.router.handlers;

import com.gelo.factory.ServiceFactory;
import com.gelo.model.domain.Review;
import com.gelo.model.domain.User;
import com.gelo.services.ReviewService;
import com.gelo.services.UserService;
import com.gelo.util.Transport;
import com.gelo.validation.forms.ReviewPaginationForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Shows some users profile with all reviews it has.
 */
public class ProfileHandler implements Handler {
    @Override
    public Transport execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String profile_id_param = request.getParameter("profile_id");
        UserService userService = ServiceFactory.getUserServiceInstance();
        ReviewService reviewService = ServiceFactory.getReviewServiceInstance();
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
                request.getParameter("order_field"),
                request.getParameter("ascending")
        );

        User profile;
        if (isOwn) {
            profile = (User) request.getSession(false).getAttribute("user");
        } else {
            profile = userService.findById(Long.parseLong(profile_id_param));
        }

        request.setAttribute("profile_user", profile);

        int all_count = reviewService.countAllByMasterId(profileId);

        request.setAttribute("page_count", Math.ceil(((double) all_count) / 20));
        request.setAttribute("all_count", all_count);

        if (form.valid()) {
            request.setAttribute("page_count", Math.ceil(((double) all_count) / form.getCount()));


            int firstPage = (form.getPage() - 1) * form.getCount();

            List<Review> reviewList = reviewService.getAllByMasterIdPaginated(
                    profileId,
                    form.getOrderField(),
                    form.getAscending(),
                    form.getCount(),
                    firstPage
            );

            request.setAttribute("order_field", form.getOrderField());
            request.setAttribute("ascending", form.getAscending());
            request.setAttribute("count", form.getCount());
            request.setAttribute("page", form.getPage());

            request.setAttribute("reviews", reviewList);
        } else {
            request.setAttribute("order_field", "id");
            request.setAttribute("ascending", true);
            request.setAttribute("count", 20);
            request.setAttribute("page", 1);

            request.setAttribute("reviews", Collections.emptyList());
            request.setAttribute("alerts", form.getErrorList());
        }

        return Transport.absForward("/profile.jsp");
    }
}
