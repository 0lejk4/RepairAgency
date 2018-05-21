package com.gelo.services.impl;

import com.gelo.mock.entity.EntityMocks;
import com.gelo.model.dao.ReviewDao;
import com.gelo.model.dao.UserDao;
import com.gelo.model.domain.Review;
import com.gelo.model.exception.DatabaseException;
import com.gelo.services.ReviewService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Collections;
import java.util.List;

public class ReviewServiceImplTest {
    @Mock
    private UserDao userDao;

    @Mock
    private ReviewDao reviewDao;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void save() throws DatabaseException {
        ReviewService reviewService = new ReviewServiceImpl(userDao, reviewDao);
        Review review = EntityMocks.createReview();

        Assert.assertTrue(reviewService.save(review));

        Mockito.verify(reviewDao).persist(review);

        Mockito.doThrow(new DatabaseException()).when(reviewDao).persist(review);

        Assert.assertFalse(reviewService.save(review));
    }

    @Test
    public void findById() throws DatabaseException {
        ReviewService reviewService = new ReviewServiceImpl(userDao, reviewDao);
        Review review = EntityMocks.createReview();

        Mockito.when(reviewDao.findByPK(3L)).thenReturn(review);

        Mockito.when(userDao.findByPK(review.getAuthor().getId())).thenReturn(review.getAuthor());

        Assert.assertEquals(reviewService.findById(3L), review);

        Mockito.doThrow(new DatabaseException()).when(reviewDao).findByPK(4L);

        Assert.assertNull(reviewService.findById(4L));


    }

    @Test
    public void getAllByMasterIdPaginated() throws DatabaseException {
        ReviewService reviewService = new ReviewServiceImpl(userDao, reviewDao);
        Review review = EntityMocks.createReview();

        List<Review> reviews = Collections.singletonList(review);

        Mockito.when(reviewDao.findAllByMasterIdPaginated(3L, "id", true, 0, 0))
                .thenReturn(reviews);

        Mockito.when(userDao.findByPK(review.getAuthor().getId())).thenReturn(review.getAuthor());

        Assert.assertTrue(reviewService.getAllByMasterIdPaginated(3L, "id", true, 0, 0).containsAll(reviews));

        Mockito.doThrow(new DatabaseException()).when(reviewDao).findAllByMasterIdPaginated(4L, "id", true, 0, 0);

        Assert.assertNull(reviewService.getAllByMasterIdPaginated(4L, "id", true, 0, 0));

    }

    @Test
    public void countAllByMasterId() throws DatabaseException {
        Long masterId = 4L;

        ReviewService reviewService = new ReviewServiceImpl(userDao, reviewDao);

        Mockito.when(reviewDao.countAllByMasterId(masterId)).thenReturn(5L);

        Assert.assertEquals(5L, reviewService.countAllByMasterId(masterId).longValue());

        Mockito.doThrow(new DatabaseException()).when(reviewDao).countAllByMasterId(masterId);

        Assert.assertEquals(0L, reviewService.countAllByMasterId(masterId).longValue());
    }

    @Test
    public void canAuthorReviewMaster() throws DatabaseException {
        ReviewService reviewService = new ReviewServiceImpl(userDao, reviewDao);

        Mockito.when(reviewDao.canAuthorReviewMaster(1L, 3L)).thenReturn(true);

        Assert.assertTrue(reviewService.canAuthorReviewMaster(1L, 3L));

        Mockito.doThrow(new DatabaseException()).when(reviewDao).canAuthorReviewMaster(1L, 3L);

        Assert.assertFalse(reviewService.canAuthorReviewMaster(1L, 3L));
    }
}