package com.gelo.model.mapper;

import com.gelo.model.domain.Order;
import com.gelo.model.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Order mapper.
 */
public class OrderMapper implements RowMapper<Order> {
    @Override
    public Order map(ResultSet rs) throws SQLException {
        return new Order(rs.getLong("id"),
                new User.UserBuilder().id(rs.getLong("author_id")).build(),
                rs.getString("author_description"),
                rs.getBigDecimal("price"),
                new User.UserBuilder().id(rs.getLong("master_id")).build(),
                new User.UserBuilder().id(rs.getLong("manager_id")).build(),
                rs.getString("manager_description"),
                rs.getBoolean("accepted"),
                rs.getBoolean("done"));
    }
}
