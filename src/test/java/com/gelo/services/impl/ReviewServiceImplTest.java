package com.gelo.services.impl;

import com.gelo.mock.entity.EntityMocks;
import com.gelo.model.dao.ReviewDao;
import com.gelo.model.dao.UserDao;
import com.gelo.model.domain.Review;
import com.gelo.persistance.ConnectionManager;
import com.gelo.persistance.exception.DatabaseRuntimeException;
import com.gelo.persistance.transaction.TransactionManager;
import com.gelo.services.ReviewService;
import com.gelo.util.BeanStorage;
import org.junit.Assert;
import org.junit.BeforeClass;
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

    @BeforeClass
    public static void setUp() {
        ConnectionManager connectionManager = new ConnectionManager();
        BeanStorage.INSTANCE.add(ConnectionManager.class, connectionManager);
        BeanStorage.INSTANCE.add(TransactionManager.class, new TransactionManager(connectionManager));
    }

    @Test
    public void save() {
        ReviewService reviewService = new ReviewServiceImpl(userDao, reviewDao);
        Review review = EntityMocks.createReview();

        Assert.assertTrue(reviewService.save(review));

        Mockito.verify(reviewDao).persist(review);

        Mockito.doThrow(new DatabaseRuntimeException()).when(reviewDao).persist(review);

        Assert.assertFalse(reviewService.save(review));
    }

    @Test
    public void findById() {
        ReviewService reviewService = new ReviewServiceImpl(userDao, reviewDao);
        Review review = EntityMocks.createReview();

        Mockito.when(reviewDao.findByPK(3L)).thenReturn(review);

        Mockito.when(userDao.findByPK(review.getAuthor().getId())).thenReturn(review.getAuthor());

        Assert.assertEquals(reviewService.findById(3L), review);

        Mockito.doThrow(new DatabaseRuntimeException()).when(reviewDao).findByPK(4L);

        Assert.assertNull(reviewService.findById(4L));


    }

    @Test
    public void getAllByMasterIdPaginated() {
        ReviewService reviewService = new ReviewServiceImpl(userDao, reviewDao);
        Review review = EntityMocks.createReview();

        List<Review> reviews = Collections.singletonList(review);

        Mockito.when(reviewDao.findAllByMasterIdPaginated(3L, "id", true, 0, 0))
                .thenReturn(reviews);

        Mockito.when(userDao.findByPK(review.getAuthor().getId())).thenReturn(review.getAuthor());

        Assert.assertTrue(reviewService.getAllByMasterIdPaginated(3L, "id", true, 0, 0).containsAll(reviews));

        Mockito.doThrow(new DatabaseRuntimeException()).when(reviewDao).findAllByMasterIdPaginated(4L, "id", true, 0, 0);

        Assert.assertNull(reviewService.getAllByMasterIdPaginated(4L, "id", true, 0, 0));

    }

    @Test
    public void countAllByMasterId() {
        Long masterId = 4L;

        ReviewService reviewService = new ReviewServiceImpl(userDao, reviewDao);

        Mockito.when(reviewDao.countAllByMasterId(masterId)).thenReturn(5L);

        Assert.assertEquals(5L, reviewService.countAllByMasterId(masterId).longValue());

        Mockito.doThrow(new DatabaseRuntimeException()).when(reviewDao).countAllByMasterId(masterId);

        Assert.assertEquals(0L, reviewService.countAllByMasterId(masterId).longValue());
    }

    @Test
    public void canAuthorReviewMaster() {
        ReviewService reviewService = new ReviewServiceImpl(userDao, reviewDao);

        Mockito.when(reviewDao.canAuthorReviewMaster(1L, 3L)).thenReturn(true);

        Assert.assertTrue(reviewService.canAuthorReviewMaster(1L, 3L));

        Mockito.doThrow(new DatabaseRuntimeException()).when(reviewDao).canAuthorReviewMaster(1L, 3L);

        Assert.assertFalse(reviewService.canAuthorReviewMaster(1L, 3L));
    }
}