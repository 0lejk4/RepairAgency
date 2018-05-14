package com.gelo.model.dao;

import com.gelo.model.exception.DatabaseException;
import com.gelo.model.domain.Review;
import com.gelo.model.exception.DatabaseException;

import java.util.List;

/**
 * The interface Review dao.
 */
public interface ReviewDao extends Connectible {
    /**
     * Persist new review.
     *
     * @param review the review
     * @throws DatabaseException the database exception
     */
    void persist(Review review) throws DatabaseException;

    /**
     * Find review by pk.
     *
     * @param id the id
     * @return the review
     * @throws DatabaseException the database exception
     */
    Review findByPK(Long id) throws DatabaseException;

    /**
     * Find all review`s by master`s id paginated.
     *
     * @param masterId the master id
     * @param limit    the limit
     * @param offset   the offset
     * @return the list
     * @throws DatabaseException the database exception
     */
    List<Review> findAllByMasterIdPaginated(Long masterId, String orderField, boolean ascending, int limit, int offset) throws DatabaseException;


    /**
     * Count all orders for master with id.
     *
     * @param masterId the master id
     * @return the int
     * @throws DatabaseException the database exception
     */
    int countAllByMasterId(Long masterId) throws DatabaseException;

    /**
     * Can author review master.
     *
     * @param authorId the author id
     * @param masterId the master id
     * @return the boolean
     * @throws DatabaseException the database exception
     */
    boolean canAuthorReviewMaster(Long authorId, Long masterId) throws DatabaseException;
}
