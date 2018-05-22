package com.gelo.model.mapper;

import com.gelo.model.domain.Role;
import com.gelo.model.domain.RoleType;
import com.gelo.model.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type User mapper.
 */
public class UserMapper implements RowMapper<User> {
    @Override
    public User map(ResultSet rs) throws SQLException {
        return new User.UserBuilder().id(rs.getLong("id"))
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .country(rs.getString("country"))
                .password(rs.getString("password"))
                .activeOrdersCount(rs.getInt("active_orders_count"))
                .summaryOrdersCount(rs.getInt("summary_orders_count"))
                .role(
                        new Role(rs.getLong("role_id"),
                                RoleType.valueOf(rs.getString("type")))
                )
                .build();
    }
}
