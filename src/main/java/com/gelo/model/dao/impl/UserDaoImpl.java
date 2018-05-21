package com.gelo.model.dao.impl;

import com.gelo.model.dao.UserDao;
import com.gelo.model.dao.generic.impl.AbstractJDBCDao;
import com.gelo.model.domain.Role;
import com.gelo.model.domain.RoleType;
import com.gelo.model.domain.User;
import com.gelo.model.exception.DatabaseException;
import com.gelo.util.constants.Queries;
import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * The type User dao.
 */
public class UserDaoImpl extends AbstractJDBCDao<User, Long> implements UserDao {
    private final static Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Override
    public String getTableName() {
        return "users";
    }

    @Override
    public String getSelectQuery() {
        return Queries.USER_SELECT;
    }

    @Override
    public String getCountQuery() {
        throw new NotImplementedException();
    }

    @Override
    public String getCreateQuery() {
        return Queries.USER_CREATE;
    }

    @Override
    public String getUpdateQuery() {
        return Queries.USER_UPDATE;
    }

    @Override
    public String getDeleteQuery() {
        throw new NotImplementedException();
    }

    @Override
    protected List<User> parseResultSet(ResultSet rs) throws DatabaseException {
        LinkedList<User> result = new LinkedList<>();
        try {
            while (rs.next()) {

                User user = parseUser(rs);

                result.add(user);
            }
        } catch (Exception e) {
            throw new DatabaseException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement ps, User user) throws DatabaseException {
        try {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getCountry());
            ps.setString(4, user.getPassword());
            ps.setLong(5, user.getRole() == null
                    ? 1
                    : user.getRole().getId());
        } catch (Exception e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement ps, User user) throws DatabaseException {
        try {
            ps.setInt(1, user.getSummaryOrdersCount());
            ps.setInt(2, user.getActiveOrdersCount());
            ps.setLong(3, user.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses user from result set
     *
     * @param rs result set to be parsed
     * @return parsed user
     * @throws SQLException happens if fields does not exist when trying to obtain it from result set
     */
    private User parseUser(ResultSet rs) throws SQLException {
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


    @Override
    public User findByEmail(String email) throws DatabaseException {
        ResultSet rs;
        User user = null;
        try (PreparedStatement ps = getConnection()
                .prepareStatement(Queries.USER_FIND_BY_EMAIL)) {
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = parseUser(rs);
            }
        } catch (SQLException e) {
            logger.error("Error obtaining user with email = " + email);
            throw new DatabaseException(
                    "%%% Exception occured in UserDao findByEmail() %%% " + e);
        }

        return user;
    }


    @Override
    public List<User> findFiveBestMasters() throws DatabaseException {
        return findAll("type = 'ROLE_MASTER'",
                "summary_orders_count",
                false,
                5,
                null);
    }

    @Override
    public boolean emailTaken(String email) throws DatabaseException {
        ResultSet rs = null;
        try (PreparedStatement ps = getConnection()
                .prepareStatement(Queries.USER_CHECK_EMAIL)) {
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error checking if email = " + email + " is taken");
            throw new DatabaseException(
                    "%%% Exception occured in UserDao emailTaken() %%% " + e);
        } finally {
            DbUtils.closeQuietly(rs);
        }


        return false;
    }
}
