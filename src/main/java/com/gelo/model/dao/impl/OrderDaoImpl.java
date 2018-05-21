package com.gelo.model.dao.impl;

import com.gelo.model.dao.OrderDao;
import com.gelo.model.dao.generic.impl.AbstractJDBCDao;
import com.gelo.model.domain.Order;
import com.gelo.model.domain.User;
import com.gelo.model.exception.DatabaseException;
import com.gelo.util.constants.Queries;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;


/**
 * The type Order dao.
 */
public class OrderDaoImpl extends AbstractJDBCDao<Order, Long> implements OrderDao {

    @Override
    public String getTableName() {
        return "orders";
    }

    @Override
    public String getSelectQuery() {
        return Queries.ORDER_SELECT;
    }

    @Override
    public String getCountQuery() {
        return Queries.ORDER_COUNT;
    }

    @Override
    public String getCreateQuery() {
        return Queries.ORDER_CREATE;
    }

    @Override
    public String getUpdateQuery() {
        return Queries.ORDER_UPDATE;
    }

    @Override
    public String getDeleteQuery() {
        throw new NotImplementedException();
    }

    @Override
    protected List<Order> parseResultSet(ResultSet rs) throws DatabaseException {
        LinkedList<Order> result = new LinkedList<>();
        try {
            while (rs.next()) {

                Order order = parseOrderFromResultSet(rs);

                result.add(order);
            }
        } catch (Exception e) {
            throw new DatabaseException(e);
        }
        return result;
    }


    @Override
    protected void prepareStatementForInsert(PreparedStatement ps, Order order) throws DatabaseException {
        try {
            ps.setLong(1, order.getAuthor().getId());
            ps.setString(2, order.getAuthorDescription());
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement ps, Order order) throws DatabaseException {
        try {
            if (order.getAuthor() == null) {
                ps.setNull(1, Types.INTEGER);
            } else {
                ps.setLong(1, order.getAuthor().getId());
            }

            ps.setString(2, order.getAuthorDescription());
            ps.setBigDecimal(3, order.getPrice());

            if (order.getMaster() == null) {
                ps.setNull(4, Types.INTEGER);
            } else {
                ps.setLong(4, order.getMaster().getId());
            }

            ps.setBoolean(5, order.isDone());

            if (order.getManager() == null) {
                ps.setNull(6, Types.INTEGER);
            } else {
                ps.setLong(6, order.getManager().getId());
            }

            ps.setString(7, order.getManagerDescription());
            ps.setBoolean(8, order.isAccepted());
            ps.setLong(9, order.getId());
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    /**
     * Parses order from result set
     *
     * @param rs result set to be parsed
     * @return parsed order
     * @throws SQLException happens if fields does not exist when trying to obtain it from result set
     */
    private Order parseOrderFromResultSet(ResultSet rs) throws SQLException {
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


    @Override
    public List<Order> findAllByAuthor(Long authorId, String orderField, boolean ascending, int limit, int offset) throws DatabaseException {
        return findAll("author_id = " + authorId, orderField, ascending, limit, offset);
    }

    @Override
    public Long countByAuthor(Long authorId) throws DatabaseException {
        return count(String.format("author_id = %d", authorId));
    }

    @Override
    public List<Order> findAllByMaster(Long masterId, String orderField, boolean ascending, int limit, int offset) throws DatabaseException {
        return findAll("master_id = " + masterId, orderField, ascending, limit, offset);
    }

    @Override
    public Long countByMaster(Long masterId) throws DatabaseException {
        return count(String.format("master_id = %d", masterId));
    }

    @Override
    public List<Order> findAllByManager(Long managerId, String orderField, boolean ascending, int limit, int offset) throws DatabaseException {
        return findAll("manager_id = " + managerId, orderField, ascending, limit, offset);
    }

    @Override
    public Long countByManager(Long managerId) throws DatabaseException {
        return count(String.format("manager_id = %d", managerId));
    }

    @Override
    public List<Order> findAllAwaitingAnswer(String orderField, boolean ascending, int limit, int offset) throws DatabaseException {
        return findAll("manager_id IS NULL", orderField, ascending, limit, offset);
    }

    @Override
    public Long countAwaitingAnswer() throws DatabaseException {
        return count("manager_id IS NULL");
    }

    @Override
    public List<Order> findAllAwaitingMaster(String orderField, boolean ascending, int limit, int offset) throws DatabaseException {
        return findAll("master_id IS NULL AND manager_id IS NOT NULL", orderField, ascending, limit, offset);
    }

    @Override
    public Long countAwaitingMaster() throws DatabaseException {
        return count("master_id IS NULL AND manager_id IS NOT NULL");
    }

}
