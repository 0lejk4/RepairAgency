package com.gelo.model.dao;

import com.gelo.model.domain.Review;

import java.util.List;

/**
 * The interface Review dao.
 */
public interface ReviewDao {
    /**
     * Persist new review.
     *
     * @param review the review
     * @ the database exception
     */
    Long persist(Review review) ;

    /**
     * Find review by pk.
     *
     * @param id the id
     * @return the review
     * @ the database exception
     */
    Review findByPK(Long id) ;

    /**
     * Find all review`s by master`s id paginated.
     *
     * @param masterId   the master id
     * @param orderField the order field
     * @param ascending  the ascending
     * @param limit      the limit
     * @param offset     the offset
     * @return the list
     * @ the database exception
     */
    List<Review> findAllByMasterIdPaginated(Long masterId, String orderField, boolean ascending, int limit, int offset) ;


    /**
     * Count all orders for master with id.
     *
     * @param masterId the master id
     * @return the Long
     * @ the database exception
     */
    Long countAllByMasterId(Long masterId) ;

    /**
     * Can author review master.
     *
     * @param authorId the author id
     * @param masterId the master id
     * @return the boolean
     * @ the database exception
     */
    boolean canAuthorReviewMaster(Long authorId, Long masterId) ;
}
