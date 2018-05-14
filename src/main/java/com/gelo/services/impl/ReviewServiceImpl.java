package com.gelo.services.impl;

import com.gelo.model.dao.ReviewDao;
import com.gelo.model.dao.UserDao;
import com.gelo.model.exception.DatabaseException;
import com.gelo.factory.DaoFactory;
import com.gelo.model.domain.Review;
import com.gelo.services.ReviewService;
import com.gelo.services.DataSource;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * The type Review service.
 */
public class ReviewServiceImpl implements ReviewService {
    private UserDao userDao;
    private ReviewDao reviewDao;

    /**
     * Instantiates a new Review service.
     */
    public ReviewServiceImpl() {
        this(DaoFactory.getUserDaoInstance(), DaoFactory.getReviewDaoInstance());
    }

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
     * @param review - review that needs author to be fetched
     * @throws DatabaseException that can occur when working with dao
     */
    private void populateAuthorField(Review review) throws DatabaseException {
        review.setAuthor(
                userDao.findByPK(review.getAuthor().getId())
        );
    }

    @Override
    public boolean save(Review review) {
        Connection connection = null;
        boolean status = true;
        try {
            connection = DataSource.getInstance().getConnection();
            connection.setAutoCommit(false);
            reviewDao.setConnection(connection);
            reviewDao.persist(review);
            connection.commit();
        } catch (SQLException | DatabaseException e) {
            DbUtils.rollbackAndCloseQuietly(connection);
            status = false;
        } finally {
            DataSource.closeConnection(connection);
        }

        return status;
    }

    @Override
    public Review findById(Long id) {
        Connection connection = null;
        Review review = null;
        try {
            connection = DataSource.getInstance().getConnection();
            connection.setAutoCommit(false);
            reviewDao.setConnection(connection);
            userDao.setConnection(connection);
            review = reviewDao.findByPK(id);
            populateAuthorField(review);
            connection.commit();
        } catch (SQLException | DatabaseException e) {
            DbUtils.rollbackAndCloseQuietly(connection);
        } finally {
            DataSource.closeConnection(connection);
        }

        return review;
    }

    @Override
    public List<Review> getAllByMasterIdPaginated(Long masterId, String orderField, boolean ascending, int limit, int offset) {
        Connection connection = null;
        List<Review> reviews;
        try {
            connection = DataSource.getInstance().getConnection();
            connection.setAutoCommit(false);
            reviewDao.setConnection(connection);
            userDao.setConnection(connection);
            reviews = reviewDao.findAllByMasterIdPaginated(masterId, orderField, ascending, limit, offset);
            for (Review r : reviews) {
                populateAuthorField(r);
            }
            connection.commit();
        } catch (SQLException | DatabaseException e) {
            DbUtils.rollbackAndCloseQuietly(connection);
            reviews = null;
        } finally {
            DataSource.closeConnection(connection);
        }

        return reviews;
    }

    @Override
    public int countAllByMasterId(Long masterId) {
        Connection connection = null;
        int count = 0;
        try {
            connection = DataSource.getInstance().getConnection();
            connection.setAutoCommit(false);
            reviewDao.setConnection(connection);
            count = reviewDao.countAllByMasterId(masterId);
            connection.commit();
        } catch (SQLException | DatabaseException e) {
            DbUtils.rollbackAndCloseQuietly(connection);
        } finally {
            DataSource.closeConnection(connection);
        }
        return count;
    }


    @Override
    public boolean canAuthorReviewMaster(Long authorId, Long masterId) {
        Connection connection = null;
        boolean can_review = false;
        try {
            connection = DataSource.getInstance().getConnection();
            connection.setAutoCommit(false);
            reviewDao.setConnection(connection);
            can_review = reviewDao.canAuthorReviewMaster(authorId, masterId);
            connection.commit();
        } catch (SQLException | DatabaseException e) {
            DbUtils.rollbackAndCloseQuietly(connection);
        } finally {
            DataSource.closeConnection(connection);
        }
        return can_review;
    }
}
