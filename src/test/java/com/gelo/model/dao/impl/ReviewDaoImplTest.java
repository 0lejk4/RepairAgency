package com.gelo.model.dao.impl;

import com.gelo.factory.DaoFactory;
import com.gelo.mock.entity.EntityMocks;
import com.gelo.model.dao.ReviewDao;
import com.gelo.model.domain.Review;
import com.gelo.model.exception.DatabaseException;
import com.gelo.services.DataSource;
import org.apache.commons.dbutils.DbUtils;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ReviewDaoImplTest {

    @Test
    public void findAllByMasterIdPaginated() throws SQLException, DatabaseException {
        Connection connection = DataSource.getInstance().getConnection();
        connection.setAutoCommit(false);

        ReviewDao reviewDao = DaoFactory.getReviewDaoInstance();
        reviewDao.setConnection(connection);

        List<Review> reviews = reviewDao.findAllByMasterIdPaginated(5L, "id", true, 2, 0);

        Assert.assertEquals(2, reviews.size());

        boolean isOrdered = true;

        for (int i = 0; i < reviews.size() - 1; i++) {
            isOrdered = reviews.get(i).getId() < reviews.get(i + 1).getId();
        }

        Assert.assertTrue(isOrdered);

        DbUtils.rollbackAndCloseQuietly(connection);
    }

    @Test
    public void countAllByMasterId() throws SQLException, DatabaseException {
        Connection connection = DataSource.getInstance().getConnection();
        connection.setAutoCommit(false);

        ReviewDao reviewDao = DaoFactory.getReviewDaoInstance();
        reviewDao.setConnection(connection);

        Long count = reviewDao.countAllByMasterId(5L);

        Assert.assertEquals(2, count.intValue());

        DbUtils.rollbackAndCloseQuietly(connection);
    }

    @Test
    public void canAuthorReviewMaster() throws SQLException, DatabaseException {
        Connection connection = DataSource.getInstance().getConnection();
        connection.setAutoCommit(false);

        ReviewDao reviewDao = DaoFactory.getReviewDaoInstance();
        reviewDao.setConnection(connection);

        boolean canAuthorReviewMaster = reviewDao.canAuthorReviewMaster(5L, 5L);

        Assert.assertFalse(canAuthorReviewMaster);

        DbUtils.rollbackAndCloseQuietly(connection);
    }

    @Test()
    public void persistFails() throws SQLException {
        Connection connection = DataSource.getInstance().getConnection();
        connection.setAutoCommit(false);

        ReviewDao reviewDao = DaoFactory.getReviewDaoInstance();
        reviewDao.setConnection(connection);


        Review review = EntityMocks.createReview();
        review.setId(6L);
        try {
            reviewDao.persist(review);
            Assert.fail();
        } catch (DatabaseException e) {
        } finally {
            DbUtils.rollbackAndCloseQuietly(connection);
        }
    }

    @Test
    public void persistSuccess() throws SQLException {
        Connection connection = DataSource.getInstance().getConnection();
        connection.setAutoCommit(false);

        ReviewDao reviewDao = DaoFactory.getReviewDaoInstance();
        reviewDao.setConnection(connection);


        Review review = EntityMocks.createReview();
        try {
            reviewDao.persist(review);
            Assert.assertEquals(review, reviewDao.findByPK(review.getId()));
        } catch (DatabaseException e) {
        } finally {
            DbUtils.rollbackAndCloseQuietly(connection);
        }
    }

    @Test
    public void findByPK() throws DatabaseException, SQLException {
        Connection connection = DataSource.getInstance().getConnection();
        connection.setAutoCommit(false);

        ReviewDao reviewDao = DaoFactory.getReviewDaoInstance();
        reviewDao.setConnection(connection);

        Review review = reviewDao.findByPK(6L);

        Assert.assertEquals(6, review.getId().intValue());
        Assert.assertEquals(3, review.getAuthor().getId().intValue());
        Assert.assertTrue(review.getTitle().startsWith("Гарний майстер"));

        DbUtils.rollbackAndCloseQuietly(connection);
    }

}