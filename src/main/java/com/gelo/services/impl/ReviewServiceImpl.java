package com.gelo.services.impl;

import com.gelo.model.dao.ReviewDao;
import com.gelo.model.dao.UserDao;
import com.gelo.model.domain.Review;
import com.gelo.persistance.exception.DatabaseRuntimeException;
import com.gelo.persistance.exception.TransactionRuntimeException;
import com.gelo.persistance.transaction.TransactionManager;
import com.gelo.services.ReviewService;
import com.gelo.util.BeanStorage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * The type Review service.
 */
public class ReviewServiceImpl implements ReviewService {
    private static final Logger logger = LogManager.getLogger(ReviewServiceImpl.class);
    private UserDao userDao;
    private ReviewDao reviewDao;
    private TransactionManager transactionManager = BeanStorage.INSTANCE.get(TransactionManager.class);

    /**
     * Instantiates a new Review service.
     *
     * @param userDao   the user dao
     * @param reviewDao the review dao
     */
    public ReviewServiceImpl(UserDao userDao, ReviewDao reviewDao) {
        this.userDao = userDao;
        this.reviewDao = reviewDao;
    }

    /**
     * Fetches author of review
     *
     * @param review - review that needs author to be fetched
     */
    private void populateAuthorField(Review review) {
        review.setAuthor(
                userDao.findByPK(review.getAuthor().getId())
        );
    }

    @Override
    public boolean save(Review review) {
        try {
            reviewDao.persist(review);
        } catch (DatabaseRuntimeException e) {
            logger.error(e);
            return false;
        }
        return true;
    }

    @Override
    public Review findById(Long id) {
        Object[] result = new Object[1];
        try {
            transactionManager.tx(() -> {
                Review review = reviewDao.findByPK(id);
                populateAuthorField(review);
                result[0] = review;
            });
        } catch (DatabaseRuntimeException | TransactionRuntimeException e) {
            logger.error(e);
            return null;
        }
        return (Review) result[0];
    }

    @Override
    public List<Review> getAllByMasterIdPaginated(Long masterId, String orderField, boolean ascending, int limit, int offset) {
        Object[] result = new Object[1];
        try {
            transactionManager.tx(() -> {
                List<Review> reviews = reviewDao.findAllByMasterIdPaginated(masterId, orderField, ascending, limit, offset);
                for (Review r : reviews) {
                    populateAuthorField(r);
                }
                result[0] = reviews;
            });
        } catch (DatabaseRuntimeException | TransactionRuntimeException e) {
            logger.error(e);
            return null;
        }

        return (List<Review>) result[0];
    }

    @Override
    public Long countAllByMasterId(Long masterId) {
        try {
            return reviewDao.countAllByMasterId(masterId);
        } catch (DatabaseRuntimeException | TransactionRuntimeException e) {
            logger.error(e);
            return 0L;
        }
    }


    @Override
    public boolean canAuthorReviewMaster(Long authorId, Long masterId) {
        try {
            return reviewDao.canAuthorReviewMaster(authorId, masterId);
        } catch (DatabaseRuntimeException | TransactionRuntimeException e) {
            logger.error(e);
            return false;
        }
    }
}
