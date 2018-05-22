package com.gelo.model.mapper;

import com.gelo.model.domain.Review;
import com.gelo.model.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Review mapper.
 */
public class ReviewMapper implements RowMapper<Review> {
    @Override
    public Review map(ResultSet rs) throws SQLException {
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
}
