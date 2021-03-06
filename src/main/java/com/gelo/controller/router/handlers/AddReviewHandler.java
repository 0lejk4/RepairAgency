package com.gelo.controller.router.handlers;

import com.gelo.controller.router.annotation.PostMapping;
import com.gelo.controller.router.annotation.PreAuthorize;
import com.gelo.model.domain.Review;
import com.gelo.model.domain.RoleType;
import com.gelo.model.domain.User;
import com.gelo.services.ReviewService;
import com.gelo.util.BeanStorage;
import com.gelo.util.Transport;
import com.gelo.util.constants.Paths;
import com.gelo.validation.Alert;
import com.gelo.validation.formats.LongFormat;
import com.gelo.validation.forms.ReviewForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handles creation of new review for masters work.
 * Prevents this action if user don`t have any orders
 * done by master or user already wrote review.
 * Also prevents self reviewing.
 * All form errors are controlled by ReviewForm class
 */
@PostMapping
@PreAuthorize(role = RoleType.ROLE_USER)
public class AddReviewHandler implements Handler {
    private ReviewService reviewService = BeanStorage.INSTANCE.get(ReviewService.class);
    @Override
    public Transport execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = (User) request.getSession(false).getAttribute("user");
        LongFormat format = new LongFormat(request.getParameter("masterId"));


        boolean can_review;
        if(format.valid()) {
            can_review = reviewService.canAuthorReviewMaster(user.getId(), format.parseLong());
        }else {
            request.setAttribute("alerts", Alert.single(Alert.danger("master.id.incorrect")));
            return Transport.absolute(Paths.PROFILE_PAGE);
        }

        Transport transport = Transport.absolute("/app/profile?profile_id=" + format.parseLong());

        if(!can_review){
            request.setAttribute("alerts", Alert.single(Alert.danger("cant.review")));
            return transport;
        }

        ReviewForm form = new ReviewForm(
                request.getParameter("masterId"),
                request.getParameter("title"),
                request.getParameter("text"),
                request.getParameter("rating")
        );


        if (form.valid()) {
            Review review = form.parseReview();

            if (review.getMasterId().equals(user.getId())) {
                request.setAttribute("alerts", Alert.single(Alert.danger("cant.review.self")));
                return transport;
            }
            review.setAuthor(user);
            reviewService.save(review);
        } else {
            request.setAttribute("alerts", form.getErrorList());
        }

        return transport;
    }
}
