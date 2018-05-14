package com.gelo.model.dao.impl;

import com.gelo.model.dao.generic.impl.AbstractJDBCDao;
import com.gelo.model.dao.ReviewDao;
import com.gelo.model.exception.DatabaseException;
import com.gelo.model.domain.Review;
import com.gelo.model.domain.User;
import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ReviewDaoImpl extends AbstractJDBCDao<Review, Long> implements ReviewDao {
    private Logger logger = Logger.getLogger(ReviewDaoImpl.class);

    @Override
    public String getTableName() {
        return "reviews";
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * from reviews";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO reviews ( master_id, author_id, title, text, rating ) VALUES ( ?, ? ,? ,? , CAST(? AS star) );";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE reviews SET rating = ?, text = ?, title = ? where id = ? ";
    }

    @Override
    public String getDeleteQuery() {
        throw new NotImplementedException();
    }

    @Override
    protected List<Review> parseResultSet(ResultSet rs) throws DatabaseException {
        LinkedList<Review> result = new LinkedList<>();
        try {
            while (rs.next()) {
                Review review = parseReview(rs);

                result.add(review);
            }
        } catch (Exception e) {
            throw new DatabaseException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement ps, Review review) throws DatabaseException {
        try {
            ps.setLong(1, review.getMasterId());
            ps.setLong(2, review.getAuthor().getId());
            ps.setString(3, review.getTitle());
            ps.setString(4, review.getText());
            ps.setString(5, review.getRating().toString());
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement ps, Review review) throws DatabaseException {
        try {
            ps.setString(1, review.getRating().toString());
            ps.setString(2, review.getText());
            ps.setString(3, review.getTitle());
            ps.setLong(4, review.getId());
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    /**
     * Parses review from result set
     *
     * @param rs result set to be parsed
     * @return parsed review
     * @throws SQLException happens if fields does not exist when trying to obtain it from result set
     */
    private Review parseReview(ResultSet rs) throws SQLException {
        return new Review(
                rs.getLong("id"),
                new User.UserBuilder()
                        .id(rs.getLong("author_id"))
                        .build(),
                rs.getString("title"),
                rs.getString("text"),
                Review.Star.valueOf(rs.getString("rating"))
        );
    }

    @Override
    public List<Review> findAllByMasterIdPaginated(Long masterId, String orderField, boolean ascending, int limit, int offset) throws DatabaseException {
        return findAll("master_id = " + masterId, orderField, ascending, limit, offset);
    }


    @Override
    public int countAllByMasterId(Long masterId) throws DatabaseException {
        ResultSet rs = null;
        int count = 0;
        try (PreparedStatement ps = getConnection()
                .prepareStatement("SELECT count(*) FROM reviews where master_id = ?")) {

            ps.setLong(1, masterId);

            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Error getting review with id = " + masterId);
            throw new DatabaseException(
                    "%%% Exception occured in ReviewDao findAllByMasterIdPaginated() %%% " + e);
        } finally {
            DbUtils.closeQuietly(rs);
        }


        return count;
    }

    @Override
    public boolean canAuthorReviewMaster(Long authorId, Long masterId) throws DatabaseException {
        ResultSet rs = null;
        boolean can_leave_review = false;
        try (PreparedStatement ps = getConnection()
                .prepareStatement("SELECT reviews_count < orders_count as can_leave_review from (select count(*) from reviews where reviews.master_id = ? and reviews.author_id = ?) reviews_count, (select count(*) from orders where orders.master_id = ? and orders.author_id = ?) orders_count;")) {

            ps.setLong(1, masterId);
            ps.setLong(2, authorId);
            ps.setLong(3, masterId);
            ps.setLong(4, authorId);
            rs = ps.executeQuery();
            while (rs.next()) {
                can_leave_review = rs.getBoolean(1);
            }
        } catch (SQLException e) {
            logger.error("Error getting review with id = " + masterId);
            throw new DatabaseException(
                    "%%% Exception occured in ReviewDao findAllByMasterIdPaginated() %%% " + e);
        } finally {
            DbUtils.closeQuietly(rs);
        }


        return can_leave_review;
    }

}
