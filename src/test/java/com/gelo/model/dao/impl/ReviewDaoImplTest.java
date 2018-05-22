package com.gelo.model.dao.impl;

import com.gelo.factory.DaoFactory;
import com.gelo.mock.entity.EntityMocks;
import com.gelo.model.dao.ReviewDao;
import com.gelo.model.domain.Review;
import com.gelo.persistance.ConnectionManager;
import com.gelo.persistance.exception.DatabaseRuntimeException;
import com.gelo.persistance.util.ScriptRunner;
import com.gelo.util.ResourcesUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class ReviewDaoImplTest {
    private DaoFactory daoFactory;

    @Before
    public void setUp() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager();
        daoFactory = new DaoFactory(connectionManager);
        ScriptRunner scriptRunner = new ScriptRunner(connectionManager);
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("SetupDB.sql"));
    }

    @Test
    public void findAllByMasterIdPaginated() throws SQLException, DatabaseRuntimeException {
        ReviewDao reviewDao = daoFactory.getReviewDaoInstance();

        List<Review> reviews = reviewDao.findAllByMasterIdPaginated(5L, "id", true, 2, 0);

        Assert.assertEquals(2, reviews.size());

        boolean isOrdered = true;

        for (int i = 0; i < reviews.size() - 1; i++) {
            isOrdered = reviews.get(i).getId() < reviews.get(i + 1).getId();
        }

        Assert.assertTrue(isOrdered);

    }

    @Test
    public void countAllByMasterId() throws SQLException, DatabaseRuntimeException {
        ReviewDao reviewDao = daoFactory.getReviewDaoInstance();

        Long count = reviewDao.countAllByMasterId(5L);

        Assert.assertEquals(2, count.intValue());

    }

    @Test
    public void canAuthorReviewMaster() throws SQLException, DatabaseRuntimeException {
        ReviewDao reviewDao = daoFactory.getReviewDaoInstance();

        boolean canAuthorReviewMaster = reviewDao.canAuthorReviewMaster(5L, 5L);

        Assert.assertFalse(canAuthorReviewMaster);
    }

    @Test()
    public void persistFails() throws SQLException {
        ReviewDao reviewDao = daoFactory.getReviewDaoInstance();

        Review review = EntityMocks.createReview();
        review.setId(6L);
        try {
            reviewDao.persist(review);
            Assert.fail();
        } catch (DatabaseRuntimeException e) {
        }
    }

    @Test
    public void persistSuccess() throws SQLException {
        ReviewDao reviewDao = daoFactory.getReviewDaoInstance();

        Review review = EntityMocks.createReview();
        review.setMasterId(3L);
        Long id = reviewDao.persist(review);
        Review actual = reviewDao.findByPK(id);
        Assert.assertEquals(review.getId(), actual.getId());
        Assert.assertEquals(review.getTitle(), actual.getTitle());
        Assert.assertEquals(review.getText(), actual.getText());
        Assert.assertEquals(review.getRating(), actual.getRating());
    }

    @Test
    public void findByPK() throws DatabaseRuntimeException, SQLException {
        ReviewDao reviewDao = daoFactory.getReviewDaoInstance();

        Review review = reviewDao.findByPK(6L);

        Assert.assertEquals(6, review.getId().intValue());
        Assert.assertEquals(3, review.getAuthor().getId().intValue());
        Assert.assertTrue(review.getTitle().startsWith("Гарний майстер"));
    }

}