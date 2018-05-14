package com.gelo.services;

import com.gelo.model.domain.Review;

import java.util.List;

/**
 * The interface Review service.
 */
public interface ReviewService {

    /**
     * Save boolean.
     *
     * @param review the review
     * @return the boolean
     */
    boolean save(Review review);

    /**
     * Find by id review.
     *
     * @param id the id
     * @return the review
     */
    Review findById(Long id);


    /**
     * Gets all by master id paginated.
     *
     * @param masterId   the master id
     * @param orderField the order field
     * @param ascending  the ascending
     * @param limit      the limit
     * @param offset     the offset
     * @return the all by master id paginated
     */
    List<Review> getAllByMasterIdPaginated(Long masterId, String orderField, boolean ascending, int limit, int offset);


    /**
     * Count all by master id int.
     *
     * @param masterId the master id
     * @return the int
     */
    int countAllByMasterId(Long masterId);

    /**
     * Can author review master boolean.
     *
     * @param authorId the author id
     * @param masterId the master id
     * @return the boolean
     */
    boolean canAuthorReviewMaster(Long authorId, Long masterId);
}
