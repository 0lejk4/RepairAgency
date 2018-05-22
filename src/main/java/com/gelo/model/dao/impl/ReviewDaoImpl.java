package com.gelo.model.dao.impl;

import com.gelo.model.dao.ReviewDao;
import com.gelo.model.dao.generic.GenericDao;
import com.gelo.model.domain.Review;
import com.gelo.model.mapper.BooleanMapper;
import com.gelo.model.mapper.LongMapper;
import com.gelo.model.mapper.ReviewMapper;
import com.gelo.persistance.ConnectionManager;
import com.gelo.persistance.JdbcTemplate;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * The type Review dao.
 */
public class ReviewDaoImpl implements ReviewDao, GenericDao<Review, Long> {
    private final JdbcTemplate jdbcTemplate;
    private final ReviewMapper reviewMapper = new ReviewMapper();
    private final BooleanMapper booleanMapper = new BooleanMapper();
    private final LongMapper longMapper = new LongMapper();

    private static final String FIND_BY_ID = "SELECT * FROM reviews where reviews.id = ?;";
    private static final String FIND_ALL_BY_MASTER = "SELECT * FROM reviews where reviews.master_id = ? ";
    private static final String COUNT_ALL_BY_MASTER = "SELECT count(*) FROM reviews where reviews.master_id = ?;";
    private static final String REVIEW_CREATE = "INSERT INTO reviews ( master_id, author_id, title, text, rating ) VALUES ( ?, ? ,? ,? , CAST(? AS star) );";
    private static final String CAN_REVIEW = "SELECT reviews_count < orders_count as can_leave_review " +
            "from (select count(*) from reviews where reviews.master_id = ? and reviews.author_id = ?) reviews_count," +
            " (select count(*) from orders where orders.master_id = ? and orders.author_id = ? and orders.done = true) orders_count;";


    /**
     * Instantiates a new Review dao.
     *
     * @param connectionManager the connection manager
     */
    public ReviewDaoImpl(ConnectionManager connectionManager) {
        jdbcTemplate = new JdbcTemplate(connectionManager);
    }

    @Override
    public Long persist(Review review) {
        Long id = jdbcTemplate.insert(REVIEW_CREATE,
                review.getMasterId(),
                review.getAuthor().getId(),
                review.getTitle(),
                review.getText(),
                review.getRating().toString());
        review.setId(id);
        return id;
    }

    @Override
    public Review findByPK(Long id) {
        return jdbcTemplate.queryObject(FIND_BY_ID, reviewMapper, id);
    }

    @Override
    public void update(Review object) {
        throw new NotImplementedException();
    }

    @Override
    public void delete(Review object) {
        throw new NotImplementedException();
    }

    @Override
    public List<Review> findAll() {
        throw new NotImplementedException();
    }

    @Override
    public List<Review> findAllByMasterIdPaginated(Long masterId, String orderField, boolean ascending, int limit, int offset) {
        return jdbcTemplate.queryObjectsPaginated(FIND_ALL_BY_MASTER, reviewMapper, orderField, ascending, limit, offset, masterId);
    }


    @Override
    public Long countAllByMasterId(Long masterId) {
        return jdbcTemplate.queryObject(COUNT_ALL_BY_MASTER, longMapper, masterId);
    }

    @Override
    public boolean canAuthorReviewMaster(Long authorId, Long masterId) {
        return jdbcTemplate.queryObject(CAN_REVIEW, booleanMapper, masterId, authorId, masterId, authorId);
    }

}
